void CleanUpOnError(int level)
{
   printf("Error encountered, cleaning up.\n");
   switch ( level )
      {
       case 1:
           printf("Could not get current working directory.\n");
           break;
       case 2:
           printf("Could not create file %s.\n",TEST_FILE);
           break;
       case 3:
           printf("Could not write to file %s.\n",TEST_FILE);
           close(FilDes);
           unlink(TEST_FILE);
           break;
       case 4:
           printf("Could not close file %s.\n",TEST_FILE);
           close(FilDes);
           unlink(TEST_FILE);
           break;
       case 5:
           printf("Could not make directory %s.\n",NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 6:
           printf("Could not change to directory %s.\n",NEW_DIRECTORY);
           rmdir(NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 7:
           printf("Could not create link %s to %s.\n",LinkName,InitialFile);
           chdir(PARENT_DIRECTORY);
           rmdir(NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 8:
           printf("Could not open link %s.\n",LinkName);
           unlink(LinkName);
           chdir(PARENT_DIRECTORY);
           rmdir(NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 9:
           printf("Could not read link %s.\n",LinkName);
           close(FilDes);
           unlink(LinkName);
           chdir(PARENT_DIRECTORY);
           rmdir(NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 10:
           printf("Could not close link %s.\n",LinkName);
           close(FilDes);
           unlink(LinkName);
           chdir(PARENT_DIRECTORY);
           rmdir(NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 11:
           printf("Could not unlink link %s.\n",LinkName);
           unlink(LinkName);
           chdir(PARENT_DIRECTORY);
           rmdir(NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 12:
           printf("Could not change to directory %s.\n",PARENT_DIRECTORY);
           chdir(PARENT_DIRECTORY);
           rmdir(NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 13:
           printf("Could not remove directory %s.\n",NEW_DIRECTORY);
           rmdir(NEW_DIRECTORY);
           unlink(TEST_FILE);
           break;
       case 14:
           printf("Could not unlink file %s.\n",TEST_FILE);
           unlink(TEST_FILE);
           break;
       default:
           break;
      }
   printf("Program ended with Error.\n"\
          "All test files and directories may not have been removed.\n");
}
 
int main ()
{ /* Get and print the real user id with the getuid() function. */
   UserID = getuid();
   printf("The real user id is %u. \n",UserID);
 
/* Get the current working directory and store it in InitialDirectory. */
   if ( NULL == getcwd(InitialDirectory,BUFFER_SIZE) ) {
      perror("getcwd Error");
      CleanUpOnError(1);
      return 0;
      }
   printf("The current working directory is %s. \n",InitialDirectory);
   
/* Create the file TEST_FILE for writing, if it does not exist.
   Give the owner authority to read, write, and execute. */
   FilDes = open(TEST_FILE, O_WRONLY | O_CREAT | O_EXCL, S_IRWXU);
   if ( -1 == FilDes ) {
      perror("open Error");
      CleanUpOnError(2);
      return 0;
      }
   printf("Created %s in directory %s.\n",TEST_FILE,InitialDirectory);
 
/* Write TEST_DATA to TEST_FILE via FilDes */
   BytesWritten = write(FilDes,TEST_DATA,strlen(TEST_DATA));
   if ( -1 == BytesWritten ) {
      perror("write Error");
      CleanUpOnError(3);
      return 0;
      }
   printf("Wrote %s to file %s.\n",TEST_DATA,TEST_FILE);
 
/* Close TEST_FILE via FilDes */
   if ( -1 == close(FilDes) ) {
      perror("close Error");
      CleanUpOnError(4);
      return 0;
      }
   FilDes = -1;
   printf("File %s closed.\n",TEST_FILE);
 
/* Make a new directory in the current working directory and
   grant the owner read, write and execute authority */
   if ( -1 == mkdir(NEW_DIRECTORY, S_IRWXU) ) {
      perror("mkdir Error");
      CleanUpOnError(5);
      return 0;
      }
   printf("Created directory %s in directory %s.\n",NEW_DIRECTORY,InitialDirectory);
 
/* Change the current working directory to the
   directory NEW_DIRECTORY just created. */
   if ( -1 == chdir(NEW_DIRECTORY) ) {
      perror("chdir Error");
      CleanUpOnError(6);
      return 0;
      }
   printf("Changed to directory %s/%s.\n",InitialDirectory,NEW_DIRECTORY);
 
/* Copy PARENT_DIRECTORY to InitialFile and
   append  "/" and TEST_FILE to InitialFile. */
   strcpy(InitialFile,PARENT_DIRECTORY);
   strcat(InitialFile,"/");
   strcat(InitialFile,TEST_FILE);
 
/* Copy USER_ID to LinkName then append the
   UserID as a string to LinkName. */
   strcpy(LinkName, USER_ID);
   sprintf(Buffer, "%d\0", (int)UserID);
   strcat(LinkName, Buffer);
 
/* Create a link to the InitialFile name with the LinkName. */
   if ( -1 == link(InitialFile,LinkName) ) {
      perror("link Error");
      CleanUpOnError(7);
      return 0;
      }
   printf("Created a link %s to %s.\n",LinkName,InitialFile);
 
/* Open the LinkName file for reading only. */
   if ( -1 == (FilDes = open(LinkName,O_RDONLY)) ) {
      perror("open Error");
      CleanUpOnError(8);
      return 0;
      }
   printf("Opened %s for reading.\n",LinkName);
 
/* Read from the LinkName file, via FilDes, into Buffer. */
   BytesRead = read(FilDes,Buffer,sizeof(Buffer));
   if ( -1 == BytesRead ) {
      perror("read Error");
      CleanUpOnError(9);
      return 0;
      }
   printf("Read %s from %s.\n",Buffer,LinkName);
   if ( BytesRead != BytesWritten ) {
      printf("WARNING: the number of bytes read is "\
             "not equal to the number of bytes written.\n");
      }
 
/* Close the LinkName file via FilDes. */
   if ( -1 == close(FilDes) ) {
      perror("close Error");
      CleanUpOnError(10);
      return 0;
      }
   FilDes = -1;
   printf("Closed %s.\n",LinkName);
 
/* Unlink the LinkName link to InitialFile. */
   if ( -1 == unlink(LinkName) ) {
      perror("unlink Error");
      CleanUpOnError(11);
      return 0;
      }
   printf("%s is unlinked.\n",LinkName);
 
/* Change the current working directory
   back to the starting directory. */
   if ( -1 == chdir(PARENT_DIRECTORY) ) {
      perror("chdir Error");
      CleanUpOnError(12);
      return 0;
      }
   printf("changing directory to %s.\n",InitialDirectory);
 
/* Remove the directory NEW_DIRECTORY */
   if ( -1 == rmdir(NEW_DIRECTORY) ) {
      perror("rmdir Error");
      CleanUpOnError(13);
      return 0;
      }
   printf("Removing directory %s.\n",NEW_DIRECTORY);
 
/* Unlink the file TEST_FILE */
   if ( -1 == unlink(TEST_FILE) ) {
      perror("unlink Error");
      CleanUpOnError(14);
      return 0;
      }
   printf("Unlinking file %s.\n",TEST_FILE);
   printf("Program completed successfully.\n");
   return 0;
}

