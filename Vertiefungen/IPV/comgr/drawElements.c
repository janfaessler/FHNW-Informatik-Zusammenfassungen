// Transform 2 units to the left & rebuild mvp
_modelViewMatrix.translate(1.5f, 0.0f, 0.0f);
mvp.setMatrix(_projectionMatrix * _modelViewMatrix);
   
// Pass updated mvp and set the red color
glUniformMatrix4fv(_mvpLoc, 1, 0, (float*)&mvp);
glUniform4f(_matDiffLoc, 0.0f, 1.0f, 0.0f, 1.0f);

// Set the vertex attribute pointers to the array of structs
GLsizei stride = sizeof(VertexPN);
glVertexAttribPointer(_pLoc, 3, GL_FLOAT, GL_FALSE, stride, &_v[0].p.x);
glVertexAttribPointer(_nLoc, 3, GL_FLOAT, GL_FALSE, stride, &_v[0].n.x);

// Draw cube with triangles by indexes
glDrawElements(GL_TRIANGLES, 36, GL_UNSIGNED_BYTE, &_i);