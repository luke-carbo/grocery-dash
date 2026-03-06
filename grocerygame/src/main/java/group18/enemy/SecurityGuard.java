package group18.Entity_Dynamic.enemy;

public class SecurityGuard extends Enemy {
    private int detectionRange;

    public SecurityGuard(int x, int y, int detectionRange){
        super(x,y);
        this.detectionRange = detectionRange;
    }
}