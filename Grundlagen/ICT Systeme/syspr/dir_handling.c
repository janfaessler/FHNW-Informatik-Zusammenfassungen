int main( int argc, char *argv[] ) {
    DIR *pDIR;
    struct dirent *pDirEnt;
    /* Open the current directory */
    pDIR = opendir(".");
    if ( pDIR == NULL ) {
        fprintf( stderr, "%s %d: opendir() failed (%s)\n", __FILE__, __LINE__, strerror( errno ));
        exit( -1 );
    }
    /* Get each directory entry from pDIR and print its name */
    pDirEnt = readdir( pDIR );
    while ( pDirEnt != NULL ) {
        printf( "%s\n", pDirEnt->d_name );
        pDirEnt = readdir( pDIR );
    }
    /* Release the open directory */
    closedir( pDIR );
    return 0;
}
