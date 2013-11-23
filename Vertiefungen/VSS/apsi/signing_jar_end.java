grant CodeBase "file:./..." SignedBy "Susan" {
	permission java.io.FilePermission "C:\\TestData\\*", "read";
}
VM-Argumente:
java -Djava.security.manager -Djava.security.policy=raypolicy -cp sCount.jar Count C:\TestData\data