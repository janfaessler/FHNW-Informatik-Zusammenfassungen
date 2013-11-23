main () {
	setjmp (position);
	signal (SIGINT, goback); /* set signal action correctly */
	printf ("Signal set back to goback\n");
	display_main_menu();
}
goback (signo) int signo; {
	signal (SIGINT, SIG_IGN);
	printf ("\nInterrupted by signal %d\n", signo);
    fflush (stdout);
	longjmp (position, 1); /* jump back to saved position */
}
display_main_menu () {
	fflush (stdout);
	printf ("Waiting in main menu: ");
	fflush (stdout);
    pause();
}
