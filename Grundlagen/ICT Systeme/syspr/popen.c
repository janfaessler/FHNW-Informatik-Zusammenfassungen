#include <stdio.h>

#define MAXLEN		255		/* maximum filename length */
#define MAXCMD		100		/* maximum length of command */
#define MAXLINE	100			/* maximum number of files */
#define ERROR		(-1)
#define SUCCESS	0

getlist (namepart, dirnames, maxnames)
	char *namepart;	/* additional part of ls command */
	char dirnames[][MAXLEN+1];  /* to hold file names */
	int  maxnames;              /* max. no. of file names */
{
	char *strcpy(), *strncat(), *fgets();
	char cmd[MAXCMD+1], inline[MAXLEN+2];
	int  i;
	FILE *lsf, *popen();
	
	strcpy (cmd, "ls "); /* first build command */

	/* add additional part of command */
	if (namepart != NULL) strncat (cmd, namepart, MAXCMD - strlen (cmd));

	/* start up command */
	if (( lsf = popen (cmd, "r")) == NULL) return (ERROR);
	
	for (i=0; i < maxnames; ++i) {
	    /* read a line */
	    if (fgets (inline, MAXLEN+2, lsf) == NULL) break;

	    /* remove newline */
	    if (inline[strlen (inline) -1] == '\n') inline [strlen (inline) -1] = '\0';

	    strcpy (&dirnames[i][0], inline);
	}
	if (i < maxnames) dirnames [i][0] = '\0';
	pclose (lsf);
	return (SUCCESS);
}

main () {
	char	namebuf[MAXLINE][MAXLEN+1]; int	i = 0;
	getlist ("*.c", namebuf, MAXLINE);
	while (namebuf[i][0] != '\0') printf ("%s\n", namebuf[i++]);
}

