main (argc, argv) int argc; char *argv[]; {
int fd, i; char read_buffer[RUNS]; struct stat fileStat;
for (i = 0; i < RUNS; ++i) read_buffer[i] = '\0';
if (argc != 2) { printf ("Missing file name, exiting\n"); return (-1); }
if ((fd = creat(argv[1], S_IRUSR)) == -1) { /* user has read rights */
   perror ("open failed"); return (-1);
} else printf ("file %s created, obtained file descriptor nr. %d\n", argv[1], fd);
if (chmod (argv[1], 0755) == -1) { perror ("chown failed"); return (-1); } 
else printf ("mode of file %s changed to -rwxr-xr-x\n", argv[1]);
for (i = 0; i < RUNS; ++i) {
    write (fd, BUFFER, sizeof (BUFFER));
    write (fd, "\n", 1);
}
if (fstat (fd, &fileStat) == -1) { perror ("fstat failed"); return (-1); } 
else {
    printf("Information for %s\n",argv[1]);
    printf("---------------------------\n");
    printf("File Size: \t\t%d bytes\n",(int) fileStat.st_size);
    printf("Number of Links: \t%d\n",fileStat.st_nlink);
    printf("File inode: \t\t%d\n",(int) fileStat.st_ino);
    printf("File Permissions: \t");
    printf( (S_ISDIR(fileStat.st_mode)) ? "d" : "-");
    printf( (fileStat.st_mode & S_IRUSR) ? "r" : "-");
    printf( (fileStat.st_mode & S_IWUSR) ? "w" : "-");
    printf( (fileStat.st_mode & S_IXUSR) ? "x" : "-");
    printf( (fileStat.st_mode & S_IRGRP) ? "r" : "-");
    printf( (fileStat.st_mode & S_IWGRP) ? "w" : "-");
    printf( (fileStat.st_mode & S_IXGRP) ? "x" : "-");
    printf( (fileStat.st_mode & S_IROTH) ? "r" : "-");
    printf( (fileStat.st_mode & S_IWOTH) ? "w" : "-");
    printf( (fileStat.st_mode & S_IXOTH) ? "x" : "-");
    printf("\n");
    printf("The file %s a symbolic link\n\n", (S_ISLNK(fileStat.st_mode)) ? "is" : "is not");
}
if (lseek (fd, 4000, SEEK_END) == -1) { /* file offset reset to EOF plus 4000 */
   perror ("lseek failed");
   return (-1);
}
write (fd, "\n", 1);
for (i = 0; i < RUNS; ++i) {
    write (fd, BUFFER, sizeof (BUFFER));
    write (fd, "\n", 1);
}
if (close (fd) == -1) {
   perror ("close failed");
   return (-1);
}
}