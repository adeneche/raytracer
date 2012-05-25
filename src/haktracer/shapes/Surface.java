package haktracer.shapes;

import haktracer.Ray;

public abstract class Surface {

    /** 
     * Does a ray hit the surface ?
     * @param r	Ray being sent
     * @param tmin	minimum hit parameter to be searched for
     * @param tmax	maximum hit parameter to be searched for 
     * */
    public abstract boolean hit(Ray r, float tmin, float tmax, float time, SurfaceHitRecord rec);
    
    /**
     * does a Ray hits the surface ? allows early termination
     * @param r		Ray being sent
     * @param tmin	min hit parameter to be searched for
     * @param tmax	max hit parameter to be searched for
     */
    public abstract boolean shadowHit(Ray r, float tmin, float tmax, float time);
}
