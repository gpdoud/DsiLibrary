// Copyright (c) 2006 by Doud Systems, Inc.  All rights reserved.
package com.doudsystems.dssynchronizer;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.Iterator;

/**
 * This class will do most of the synchronization work
 * 
 * @author G
 * 
 */
public class DsSynchronizer
{
	// buffer size set by testing on a 4.7MB Access database file
	private final static int BUFFERSIZE = 1024 * 8;

	/**
	 * This is the main public method to synchronize two directories and all the
	 * files within those directories
	 * 
	 * @param dirMaster -
	 *            the master directory (read only)
	 * @param dirSlave -
	 *            the slave directory. Will be changed to match dirMaster.
	 * @return true if successful; otherwise false;
	 */
	public static boolean Synchronize(DsDirectory dirMaster, DsDirectory dirSlave)
	{
		DsLogger.message("Synchronize directory " + dirMaster.getAbsolutePath());
		return SynchronizeDirectory(dirMaster, dirSlave);
	}

	/**
	 * This is the main public method to synchronize two files
	 * 
	 * @param fileMaster -
	 *            the master file (read only)
	 * @param fileSlave -
	 *            the slave file. Will be changed to match fileMaster.
	 * @return true if successful; otherwise false;
	 */
	public static boolean Synchronize(DsFile fileMaster, DsFile fileSlave)
	{
		DsLogger.message("Synchronize file " + fileMaster.getAbsolutePath());
		return SynchronizeFile(fileMaster, fileSlave);
	}

	/**
	 * Assembles the slave directory and synchronizes the files from the master
	 * 
	 * @param dirMaster -
	 *            master DsDirectory directory
	 * @param dirSlave -
	 *            String path to the slave directory
	 * @return true if successful; otherwise false;
	 */
	private static boolean SynchronizeDirectory(DsDirectory dirMaster, DsDirectory dirSlave)
	{
		boolean success = false;
		// synchronize all the files in the base directory
		DsFiles files = dirMaster.getFiles();
		success = SynchronizeFiles(files, dirSlave.getAbsolutePath());
		// synchronize all the directories in the base directory
		DsDirectories directories = dirMaster.getDirectories();
		try
		{
			success = SynchronizeDirectories(directories, dirSlave.getAbsolutePath());
		}
		catch (DsCannotCreateDirectoryException e)
		{
			e.printStackTrace();
		}
		return true;
	}

	/**
	 * (Private) Creates the slave directory (if needed) and synchronizes all
	 * the sub directories within the master
	 * 
	 * @param masterDirs -
	 *            DsDirectories list of directories
	 * @param pathSlave -
	 *            String path of the new slave directory
	 * @return true if successful; otherwise false;
	 * @throws DsCannotCreateDirectoryException
	 *             if the new slave directory cannot be created
	 */
	private static boolean SynchronizeDirectories(DsDirectories masterDirs,
			String pathSlave) throws DsCannotCreateDirectoryException
	{
		for (Iterator iter = masterDirs.iterator(); iter.hasNext();)
		{
			DsDirectory masterDirectory = (DsDirectory) iter.next();
			String masterDirectoryName = masterDirectory.getName();
			DsDirectory slaveDirectory = new DsDirectory(pathSlave, masterDirectoryName);
			if (!slaveDirectory.exists())
			{
				slaveDirectory.mkdir();
				setDirectoryAttributes(masterDirectory, slaveDirectory);
			}
			boolean rc = Synchronize(masterDirectory, slaveDirectory);
			if (!rc)
				return false;
		}
		return true;
	}

	/**
	 * (Private) Creates the slave file (if needed) and synchronizes all the sub
	 * files within the master
	 * 
	 * @param masterFiles -
	 *            DsFiles list of files
	 * @param pathSlave -
	 *            String path of the new slave directory
	 * @return true if successful; otherwise false;
	 */
	private static boolean SynchronizeFiles(DsFiles masterFiles,
			String pathSlave)
	{
		for (Iterator iter = masterFiles.iterator(); iter.hasNext();)
		{
			DsFile masterFile = (DsFile) iter.next();
			String masterFileName = masterFile.getName();
			DsFile slaveFile = new DsFile(pathSlave, masterFileName);

			boolean rc = Synchronize(masterFile, slaveFile);
			if (!rc)
				return false;
		}
		return true;
	}

	/**
	 * Sets the attributes of the new slave directory the same as the master
	 * 
	 * @param dirMaster -
	 *            DsDirectory source
	 * @param dirSlave -
	 *            DsDirectory to be changed
	 * @return true if successful; otherwise false;
	 */
	private static boolean setDirectoryAttributes(DsDirectory dirMaster,
			DsDirectory dirSlave)
	{
		try
		{
			// if can't write to the master, set slave to read only...
			if (!dirMaster.canWrite())
				dirSlave.setReadOnly();
			// set the modified dates the same
			dirSlave.setLastModified(dirMaster.lastModified());
		}
		catch (IllegalArgumentException e)
		{
			return false;
		}
		catch (SecurityException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * Sets the attributes of the new slave file the same as the master
	 * 
	 * @param fileMaster -
	 *            DsFile source
	 * @param fileSlave -
	 *            DsFile to be changed
	 * @return true if successful; otherwise false;
	 */
	private static boolean setFileAttributes(DsFile fileMaster, DsFile fileSlave)
	{
		try
		{
			// if can't write to the master, set slave to read only...
			if (!fileMaster.canWrite())
				fileSlave.setReadOnly();
			// set the modified dates the same
			fileSlave.setLastModified(fileMaster.lastModified());
		}
		catch (IllegalArgumentException e)
		{
			return false;
		}
		catch (SecurityException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * Synchronizes two DsFile objects. fileMaster is the source and fileSlave
	 * will be changed to be exactly like fileMaster.
	 * 
	 * @param fileMaster:
	 *            DsFile source
	 * @param fileSlave:
	 *            DsFile target; after sync, should be identical to fileMaster.
	 * @return true if successful; otherwise false
	 */
	private static boolean SynchronizeFile(DsFile fileMaster, DsFile fileSlave)
	{
		boolean success = false;
		if (fileMaster.isText())
			success = SynchronizeFileText(fileMaster, fileSlave);
		else
			success = SynchronizeFileDefault(fileMaster, fileSlave);
		if (success)
			success = setFileAttributes(fileMaster, fileSlave);
		return success;
	}

	/**
	 * Specialized synchronization of text files only
	 * 
	 * @param fileMaster:
	 *            DsFile source
	 * @param fileSlave:
	 *            DsFile target; after sync, should be identical to fileMaster.
	 * @return true if successful; otherwise false
	 */
	private static boolean SynchronizeFileText(DsFile fileMaster,
			DsFile fileSlave)
	{
		int charsRead = 0;
		char[] buffer = new char[BUFFERSIZE];
		try
		{
			FileReader reader = new FileReader(fileMaster);
			FileWriter writer = new FileWriter(fileSlave);
			while ((charsRead = reader.read(buffer)) != -1)
				writer.write(buffer, 0, charsRead);
			writer.flush();
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * General synchronization routine
	 * 
	 * @param fileMaster:
	 *            DsFile source
	 * @param fileSlave:
	 *            DsFile target: after sync, should be identical to fileMaster
	 * @return true if successful; otherwise false
	 */
	private static boolean SynchronizeFileDefault(DsFile fileMaster,
			DsFile fileSlave)
	{
		byte[] buffer = new byte[BUFFERSIZE];
		int bytesRead = 0;

		try
		{
			RandomAccessFile reader = new RandomAccessFile(fileMaster, "r");
			RandomAccessFile writer = new RandomAccessFile(fileSlave, "rwd");

			while ((bytesRead = reader.read(buffer)) != -1)
				writer.write(buffer, 0, bytesRead);
			writer.close();
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}
		return true;
	}

	/**
	 * Checks whether two DsFile objects are identical
	 * 
	 * @param file1:
	 *            DsFile
	 * @param file2:
	 *            DsFile
	 * @return True if the files are identical, false otherwise
	 */
	public static boolean isIdentical(DsFile file1, DsFile file2)
	{
		return isIdentical(file1, file2, true);
	}

	/**
	 * Checks whether two DsFile objects are identical
	 * 
	 * @param file1:
	 *            DsFile
	 * @param file2:
	 *            DsFile
	 * @param includeModifiedDateCheck
	 *            Whether or not to check the last modified date for identity
	 *            The default is true
	 * @return True if the files are identical, false otherwise
	 */
	public static boolean isIdentical(DsFile file1, DsFile file2,
			boolean includeModifiedDateCheck)
	{
		// set the identical variable to true then set it to false if we find a
		// difference
		boolean identical = true;

		// if last modified dates are different, they cannot be identical
		// this check is optional because different date doesn't mean contents
		// cannot be the same.
		if (includeModifiedDateCheck)
			if (!isIdenticalLastModified(file1, file2))
				identical = false;

		// if the lengths are not equal, they cannot be identical
		if (!isLengthEqual(file1, file2))
			identical = false;

		// if the contents are not equal, they cannot be identical
		if (!isContentIdentical(file1, file2))
			identical = false;

		return identical;
	}

	private static boolean isIdenticalLastModified(DsFile file1, DsFile file2)
	{
		return file1.lastModified() == file2.lastModified();
	}

	/**
	 * Checks whether the contents of two files are identical
	 * 
	 * @param A
	 *            DsFile object
	 * @param A
	 *            DsFile object
	 * @return True if the files contents are identical, false otherwise
	 */
	private static boolean isContentIdentical(DsFile file1, DsFile file2)
	{
		byte[] fb1 = new byte[BUFFERSIZE];
		byte[] fb2 = new byte[BUFFERSIZE];
		RandomAccessFile raf1 = null;
		RandomAccessFile raf2 = null;
		try
		{
			raf1 = new RandomAccessFile(file1, "r");
			raf2 = new RandomAccessFile(file2, "r");
			int f1BytesRead = raf1.read(fb1);
			int f2BytesRead = raf2.read(fb2);

			while (true)
			{
				// if both files are at EOF at the same time, everything is the
				// same
				if (f1BytesRead == -1 && f2BytesRead == -1)
					return true;

				// if we read fewer bytes from one file, they are different
				// we'll probably never get here because we check file length
				// earlier
				// but just in case...
				if (f1BytesRead != f2BytesRead)
					return false;

				// we've read data into the buffer
				// check that the buffers are the same byte-by-byte
				// if any are different, the files are not identical
				for (int idx = 0; idx < f2BytesRead; idx++)
				{
					if (fb1[idx] != fb2[idx])
						return false;
				}

				// read the next set of bytes into the buffers
				f1BytesRead = raf1.read(fb1);
				f2BytesRead = raf2.read(fb2);
			}
		}
		catch (FileNotFoundException e)
		{
			return false;
		}
		catch (IOException e)
		{
			return false;
		}

	}

	/**
	 * Checks whether two DsFiles have the same length
	 * 
	 * @param file1
	 *            DsFile object
	 * @param file2
	 *            DsFile object
	 * @return True if the files are the same length, false otherwise
	 */
	private static boolean isLengthEqual(DsFile file1, DsFile file2)
	{
		return file1.length() == file2.length();
	}

	public DsSynchronizer()
	{
	}

}
