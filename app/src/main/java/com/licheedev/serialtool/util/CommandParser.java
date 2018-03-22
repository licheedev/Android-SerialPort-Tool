package com.licheedev.serialtool.util;

import com.licheedev.serialtool.model.Command;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by John on 2018/3/22.
 */

public class CommandParser {

    public CommandParser() {
    }

    public List<Command> parse(File file) throws IOException {

        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));

            String line;

            ArrayList<Command> commands = new ArrayList<>();

            while ((line = reader.readLine()) != null) {
                String cmd;
                String comment;
                int commentIndex = line.indexOf("//");
                if (commentIndex > -1) {
                    cmd = line.substring(0, commentIndex).replaceAll(" ", "").toUpperCase();
                    comment = line.substring(commentIndex + 2, line.length()).trim();
                } else {
                    cmd = line.replaceAll(" ", "").toUpperCase();
                    comment = "";
                }

                if (cmd.matches("[0-9a-fA-F]+")) {
                    Command command = new Command(cmd, comment);
                    commands.add(command);
                }
            }

            return commands;
        } catch (IOException e) {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e1) {
                    e1.printStackTrace();
                }
            }
            throw e;
        }
    }

    public Observable<List<Command>> rxParse(final File file) {

        return Observable.defer(new Callable<ObservableSource<List<Command>>>() {
            @Override
            public ObservableSource<List<Command>> call() throws Exception {

                return Observable.just(parse(file));
            }
        }).compose(RxUtil.<List<Command>>rxIoMain());
    }
}
