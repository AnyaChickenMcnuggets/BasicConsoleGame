package net.codestudent.main;

public abstract class Character {

    //Переменные и атрибуты для всех персонажей
    public String name;
    public int maxHp, hp, xp;

    //Конструктор для персонажей
    public Character(String name, int maxHp, int xp){
        this.name = name;
        this.maxHp = maxHp;
        this.xp = xp;
        this.hp = maxHp;
    }

    //Методы для всех персонажей
    public abstract int attack();
    public abstract int defend();

}
