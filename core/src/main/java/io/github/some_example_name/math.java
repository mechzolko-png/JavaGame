package io.github.some_example_name;

class Vec2 {
    float x;
    float y;
    Vec2 (float x, float y) {
        this.x = x;
        this.y = y;
    }
}

public class math {
    public static Vec2 normalize (float vx, float vy) {
        float vecLength = (float) Math.sqrt((vx * vx + vy * vy));
        return new Vec2((vx/vecLength),(vy/vecLength));
    }

    public static Vec2 getvelocity (Vec2 from,Vec2 to,float speed) {
        float dx = to.x -= from.x;
        float dy = to.y -= from.y;
        Vec2 normal = normalize(dx,dy);
        Vec2 vector = new Vec2(normal.x*speed, normal.y*speed);

        return vector;
    }

    public static float distance (Vec2 a, Vec2 b) {
        Vec2 d = new Vec2(Math.abs(a.x - b.x),Math.abs(a.x - a.y));
        return (float) Math.sqrt(d.x*d.x + d.y*d.y);
    }

}
