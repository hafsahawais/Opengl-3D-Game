package models;


// A 3D model stored in memory
// Need to know the VAO(Vertex array object) Id
// number of vertices in the VAO
public class RawModel {
    public int getVaoId() {
        return vaoId;
    }


    public int getVertexCount() {
        return vertexCount;
    }



    private int vaoId;
    private int vertexCount;


//    Constructor
    public RawModel(int vaoId, int vertexCount) {
        this.vaoId = vaoId;
        this.vertexCount = vertexCount;
    }
}
