package org.todo_programming.ArduinoMonitor;
import com.google.gson.Gson;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.todo_programming.Serial.SerialTemperatureComms;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

@ServerEndpoint("/socket")
public class WebsocketServer
{
    private final SensorBean data = SensorBean.getInstance();

    /* log4j instance */
    static final Logger log = LogManager.getLogger(WebsocketServer.class.getName());

    private static final Gson gson = new Gson();

    @OnOpen
    public void onOpen(Session session)
    {
        log.info("Connected ... " + session.getId());
    }

    @OnMessage
    public String onMessage(String message, Session session)
    {
        switch (message)
        {
            case "units":
                message = String.valueOf(Config.getInstance().getUnits());
                break;
            case "temp":
                message = data.getTemp();
                break;
            case "humid":
                message = data.getHumidity();
                break;
            case "airQuality":
                message = data.getAirQualityString();
                break;
            case "config":
                message = gson.toJson(Config.getInstance());
                break;
            case "subscribe":
              TimerTask task = new TimerTask()
              {
                  @Override
                  public void run()
                  {
                        if(session.isOpen())
                        {
                            try
                            {
                                session.getBasicRemote().sendText(gson.toJson(data));
                            }

                            catch (IOException e)
                            {
                                log.error("failed to send subscriber data", e);
                            }
                        }

                        else
                        {
                            this.cancel();
                        }
                  }
              };
                Timer timer = new Timer(true);
                timer.scheduleAtFixedRate(task, 0,100);
                break;
            case "quit":
                try
                {
                    session.close(new CloseReason(CloseReason.CloseCodes.NORMAL_CLOSURE, "Game ended"));
                }
                catch (IOException e)
                {
                    throw new RuntimeException(e);
                }
                break;
        }
        return message;
    }

    @OnClose
    public void onClose(Session session, CloseReason closeReason)
    {
        log.warn(String.format("Session %s closed because of %s", session.getId(), closeReason));

    }


    @OnError
    public void onError(Session session, Throwable throwable) {
        log.info(throwable.getMessage());
    }

}
