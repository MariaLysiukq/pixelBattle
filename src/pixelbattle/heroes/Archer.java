package pixelbattle.heroes;

import pixelbattle.core.Fighter;
import pixelbattle.core.Hero;
import pixelbattle.core.Skill;
import pixelbattle.core.Target;
import pixelbattle.core.Ultimate;
import pixelbattle.engine.Sprite;

/** Лучниця — швидка й спритна. */
public class Archer extends Hero {

    public Archer() {
        super("Лучниця", 80, new Sprite("elf_f_idle_anim", 4, 4));

        addSkill(new Skill("Постріл", Target.ENEMY, 0, (user, target) ->
                user.getName() + " стріляє в " + target.getName() + ": "
                        + target.takeDamage(random(10, 16)) + " шкоди"));
        addSkill(new Skill("Подвійний постріл", Target.ENEMY, 1, (user, target) -> {
            int total = target.takeDamage(random(7, 11)) + target.takeDamage(random(7, 11));
            return user.getName() + " двічі стріляє в " + target.getName() + ": " + total + " шкоди";
        }));
        setUltimate(new Ultimate() {
            private int arrows = 3;
            @Override
            public String getName() {
                return "Злива стріл";
            }
            @Override
            public String getDescription() {
                return "Кожен живий ворог отримує " + arrows + " стріл по 4-8 шкоди, з кожним разом на 1 більше";
            }
            @Override
            public String activate(Fighter user, Fighter[] allies, Fighter[] enemies) {
                int total = 0;
                for (Fighter e : enemies) {
                    for (int i = 0; i < arrows && e.isAlive(); i++) {
                        total += e.takeDamage(random(4, 8));
                    }
                }
                String msg = user.getName() + " випускає зливу стріл (" + arrows + " на ворога): " + total + " шкоди";
                arrows++;
                return msg;
            }
        });
    }

    @Override
    protected int modifyIncomingDamage(int damage) {
        return chance(25) ? 0 : damage;
    }
}
