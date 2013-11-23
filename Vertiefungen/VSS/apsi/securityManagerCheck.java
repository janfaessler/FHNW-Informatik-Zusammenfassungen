private void securityManagerCheck() {
	SecurityManager sm = System.getSecurityManager();
	if (sm != null) {
		sm.checkPermission(...);
	}
}