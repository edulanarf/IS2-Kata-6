package ulpgc.es.view;

import io.javalin.Javalin;
import ulpgc.es.control.CommandFactory;
import ulpgc.es.control.commands.WorkingDateCommand;
import ulpgc.es.control.commands.WorkingDaysCommand;

public class workingDaysService {
    private final int port;
    private final CommandFactory factory;
    private Javalin app;

    public workingDaysService(int port, CommandFactory factory) {
        this.port = port;
        this.factory = factory;
        factory.register("working-service", workingDaysBuilder());
        factory.register("working-date", workingDaysBuilder());
    }
    public void start(){
        app = Javalin.create().get("working-days",ctx-> execute("working-days", ctx.req(), ctx.res()))
                .get("working-date",ctx-> execute("working-date", ctx.req(), ctx.res()))
                .start(port);

    }

    public void stop(){ app.stop();}

    private static CommandFactory.Builder workingDateBuilder(){
        return (req,res)-> new WorkingDateCommand(WorkingDateAdapter.inputOf(req), WorkingDateAdapter.outputOf(req));
    }

    private static CommandFactory.Builder workingDaysBuilder(){
        return (req,res)-> new WorkingDaysCommand(WorkingDaysAdapter.inputOf(req), WorkingDaysAdapter.outputOf(res));
    }
}
