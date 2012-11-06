main () {
	was = signal (SIGALRM, catch); /* catch SIGALRM / save previous action */
	timed_out = FALSE;

	alarm (TIMEOUT); /* set the alarm clock */
	printf ("in time-critical section\n"); /* do something time critical */
	sleep (3); /* set to value < 5 to be OK, or > 5 to provoke overrun */
	alarm(0); /* turn alarm off */

	/* if timed_out is TRUE, then the action took too long */
	if (timed_out == TRUE) printf ("Time overrun\n");
	else {
	   signal (SIGALRM, was); /* restore old signal action */
       printf ("Just in time\n");
    }
}
catch (sig) int sig; {
	timed_out = TRUE;        /* set timeout flag */
	signal (SIGALRM, catch); /* reinitialise signal handler action */
}
