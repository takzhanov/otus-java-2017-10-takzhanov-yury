package io.github.takzhanov.umbrella.hw15.front;

import io.github.takzhanov.umbrella.hw09.domain.UserDataSet;
import io.github.takzhanov.umbrella.hw15.app.*;
import io.github.takzhanov.umbrella.hw15.ms.Address;
import io.github.takzhanov.umbrella.hw15.ms.MessageSystem;

import java.util.ArrayList;
import java.util.List;

public class FrontendServiceImpl implements FrontendService {
    private final Address address;
    private final MessageSystemContext context;

    private final List<Listener> listeners = new ArrayList<>();

    public FrontendServiceImpl(MessageSystemContext context, Address address) {
        this.context = context;
        this.address = address;
    }

    @Override
    public void init() {
        context.getMessageSystem().addAddressee(this);
    }

    @Override
    public Address getAddress() {
        return address;
    }

    @Override
    public MessageSystem getMS() {
        return context.getMessageSystem();
    }

    @Override
    public void addListener(Listener listener) {
        sayAll("New user connected");
        listeners.add(listener);
    }

    @Override
    public void removeListener(Listener listener) {
        listeners.remove(listener);
        sayAll("User disconnected");
    }

    @Override
    public void sayAll(String msg) {
        for (Listener listener : listeners) {
            listener.onFrontendUpdate(msg);
        }
    }

    @Override
    public void showSysInfo(final Listener source) {
        context.getMessageSystem().sendMessage(new MsgToDb(this.getAddress(), context.getDbAddress()) {
            @Override
            public void exec(DbService dbService) {
                String answer = dbService.getMetaData();
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dbService.getMS().sendMessage(new MsgToFrontend(this.getTo(), this.getFrom()) {
                    @Override
                    public void exec(FrontendService frontendService) {
                        source.onFrontendUpdate(answer);
                    }
                });
            }
        });
    }

    @Override
    public void showUserInfo(final Listener source, final String username) {
        context.getMessageSystem().sendMessage(new MsgToDb(this.getAddress(), context.getDbAddress()) {
            @Override
            public void exec(DbService dbService) {
                List<UserDataSet> userByName = dbService.findUserByName(username);
                String answer;
                if (userByName.size() > 0) {
                    answer = userByName.get(0).toString();
                } else {
                    answer = "User mot found";
                }
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                dbService.getMS().sendMessage(new MsgToFrontend(this.getTo(), this.getFrom()) {
                    @Override
                    public void exec(FrontendService frontendService) {
                        source.onFrontendUpdate(answer);
                    }
                });
            }
        });
    }
}
