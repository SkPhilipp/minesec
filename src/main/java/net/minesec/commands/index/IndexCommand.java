package net.minesec.commands.index;

import com.fasterxml.jackson.core.JsonGenerator;
import net.minesec.commands.shared.Command;
import net.minesec.commands.shared.Context;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Copyright (c) 30/06/2017, MineSec. All rights reserved.
 */
public class IndexCommand extends Command<IndexCommand.Args> {

    public static class Args {
    }

    private final BugBountyIndexer bugBountyIndexer;

    public IndexCommand() {
        this.bugBountyIndexer = new BugBountyIndexer();
    }

    @Override
    public String name() {
        return "index";
    }

    @Override
    public Args defaults() {
        return new Args();
    }

    @Override
    public void execute(Context context, Args args) throws IOException {
        try (JsonGenerator generator = context.createOutGenerator()) {
            generator.writeStartArray();
            Consumer<BugBounty> bugBountyConsumer = bounty -> {
                try {
                    generator.writeObject(bounty);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
                }
            };
            this.bugBountyIndexer.indexBugcrowdCurated(bugBountyConsumer);
            this.bugBountyIndexer.indexBugcrowdPrograms(bugBountyConsumer);
            this.bugBountyIndexer.indexVulnerabilityLabCurated(bugBountyConsumer);
            this.bugBountyIndexer.indexHackeroneCurated(bugBountyConsumer);
            generator.writeEndArray();
        }
    }

}
