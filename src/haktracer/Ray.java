package haktracer;

public class Ray {
	private Vec3[] data;
	private int[] posneg;
	
	public Ray() {
		data = new Vec3[3];
		data[0] = new Vec3();
		data[1] = new Vec3();
		data[2] = new Vec3();
		
		posneg = new int[6];
	}
	
	public Ray(Ray r) {
		for (int i = 0; i < 3; i++) {  
			data[i].set(r.data[i]);
		}
		
		for (int i = 0; i < 6; i++) {
			posneg[i] = r.posneg[i];
		}
	}
	
	public Ray(Vec3 a, Vec3 b) {
		this();
		
	    data[0].set(a); 
	    setDirection(b);
	}
	
	public Vec3 getOrigin() { return data[0]; }
	public Vec3 getDirection() { return data[1]; }
	public Vec3 getInvDirection() { return data[2]; }
	
	public void setOrigin(Vec3 a) { data[0].set(a); }
	
	public void setDirection(Vec3 b) {
	    data[1].set(b); 
	    for (int i = 0; i < 3; i++) {
	    	data[2].es[i] = 1f / b.es[i];
	    }
	    
	    posneg[0] =  (data[1].x() > 0 ? 0 : 1);
	    posneg[1] = posneg[0] ^ 1;
	       
	    posneg[2] =  (data[1].y() > 0 ? 0 : 1);
	    posneg[3] = posneg[2] ^ 1;  
	       
	    posneg[4] =  (data[1].z() > 0 ? 0 : 1);
	    posneg[5] = posneg[4] ^ 1;		
	}
	
    public Vec3 pointAtParameter(float t) { 
    	//return data[0] + t*data[1];
    	
    	return new Vec3(data[0].x()+t*data[1].x(), 
    		data[0].y()+t*data[1].y(),
    		data[0].z()+t*data[1].z());
    }

}
