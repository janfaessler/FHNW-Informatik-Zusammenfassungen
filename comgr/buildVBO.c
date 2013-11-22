void buildVBO(GLuint &vboID, void* dataPointer, GLint numElements, GLint elementSize, GLuint typeSize, GLuint targetTypeGL, GLuint usageTypeGL) {

	// if buffer exist delete it first
	if (vboID) glDeleteBuffers(1, &vboID);
	
	// Generate a buffer id	
	glGenBuffers(1, &vboID);
	
	// binds (activates) the buffer that is used next
	glBindBuffer(targetTypeGL, vboID);
	
	// determine the buffersize in bytes
	SLint bufSize = numElements * elementSize * typeSize;
	
	// copy data to the VBO on the GPU. The data could be delete afterwards.
	glBufferData(targetTypeGL, bufSize, dataPointer, usageTypeGL);
	glBindBuffer(targetTypeGL, 0);
}