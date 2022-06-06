package makamys.lodmod.util;

import java.nio.ByteBuffer;
import java.nio.FloatBuffer;
import java.nio.IntBuffer;
import java.nio.ShortBuffer;

public class BufferWriter {
    
    private ByteBuffer buf;
    
    private FloatBuffer floatBuffer;
    private ShortBuffer shortBuffer;
    private IntBuffer intBuffer;
    
    public BufferWriter(ByteBuffer buf) {
        this.buf = buf;
        this.floatBuffer = buf.asFloatBuffer();
        this.shortBuffer = buf.asShortBuffer();
        this.intBuffer = buf.asIntBuffer();
    }
    
    private void incrementPosition(int add) {
        buf.position(buf.position() + add);
        floatBuffer.position(buf.position() / 4);
        shortBuffer.position(buf.position() / 2);
        intBuffer.position(buf.position() / 4);
    }
    
    public void writeFloat(float x) {
        try {
        floatBuffer.put(x);
        
        incrementPosition(4);
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void writeInt(int x) {
        intBuffer.put(x);
        
        incrementPosition(4);
    }
    
}