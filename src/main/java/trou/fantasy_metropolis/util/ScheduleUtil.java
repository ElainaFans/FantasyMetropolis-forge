package trou.fantasy_metropolis.util;

import java.util.Queue;
import java.util.TimerTask;

import com.google.common.collect.Queues;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;


public class ScheduleUtil {
    private static final Queue<TimerTask> tickStartTasks = Queues.newArrayDeque();
    private static final Queue<TimerTask> tickEndTasks = Queues.newArrayDeque();

    @SubscribeEvent
    public void onServerTick(TickEvent.ServerTickEvent event) {
        if (event.phase == TickEvent.Phase.START) {
            synchronized (tickStartTasks) {
                while (!tickStartTasks.isEmpty()) {
                    tickStartTasks.poll().run();
                }
            }
        } else {
            synchronized (tickEndTasks) {
                while (!tickEndTasks.isEmpty()) {
                    tickEndTasks.poll().run();
                }
            }
        }
    }

    public static void addTask(TimerTask task, TickEvent.Phase phase) {
        if (phase == TickEvent.Phase.START) {
            synchronized (tickStartTasks) {
                tickStartTasks.add(task);
            }
        } else {
            synchronized (tickEndTasks) {
                tickEndTasks.add(task);
            }
        }
    }

    @FunctionalInterface
    public interface TickFun {

        void invoke();

    }

    public static class TickStartTask extends TimerTask {

        private int tick;
        private final TickFun fun;

        public TickStartTask(int tick, TickFun fun) {
            this.tick = tick;
            this.fun = fun;
        }

        @Override
        public void run() {
            if (--tick > 0) {
                addTask(new TickEndTask(this), TickEvent.Phase.END);
            } else {
                fun.invoke();
            }
        }

    }

    public static class TickEndTask extends TimerTask {

        private final TimerTask nextTask;

        public TickEndTask(TimerTask nextTask) {
            this.nextTask = nextTask;
        }

        @Override
        public void run() {
            addTask(nextTask, TickEvent.Phase.START);
        }

    }
}
