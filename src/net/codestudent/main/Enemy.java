package net.codestudent.main;

public class Enemy extends Character{

    //Переменная чтобы хранить опыт за врага
    int playerLvl;

    //Специальный конструктор врага
    public Enemy(String name, int playerLvl) {
        super(name, (int) (Math.random()*10 +playerLvl), (int) (Math.random()*(playerLvl + 2)+playerLvl)); //имя enemyHP и xp
        //применение переменной
        this.playerLvl = playerLvl;
    }
    //Специфичная атака и защита врагов
    @Override
    public int attack() {
        return (int) (Math.random()*(playerLvl + 3) + xp); //Максимальный базовый урон 8
    }

    @Override
    public int defend() {
        return (int) (Math.random()*playerLvl + xp); //Максимальная базовая защита 5
    }
}
