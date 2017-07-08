package net.minesec.commands.index;

import com.fasterxml.jackson.core.JsonGenerator;
import net.minesec.core.Command;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Copyright (c) 30/06/2017, MineSec. All rights reserved.
 */
public class IndexCommand extends Command<IndexCommand.Args> {

    public class Args{}

    private BugBountyIndexer bugBountyIndexer;

    public IndexCommand() {
        this.bugBountyIndexer = new BugBountyIndexer();
    }

    @Override
    public String name() {
        return "index";
    }

    @Override
    public Args argsDefault() {
        return new Args();
    }

    @Override
    public void execute(Args args) throws IOException {
        try(JsonGenerator generator = this.openGenerator()) {
            generator.writeStartArray();
            Consumer<BugBounty> bugBountyConsumer = bounty -> {
                try {
                    generator.writeObject(bounty);
                } catch (IOException e) {
                    exit(e);
                }
            };
            bugBountyIndexer.indexBugcrowdCurated(bugBountyConsumer);
            bugBountyIndexer.indexBugcrowdPrograms(bugBountyConsumer);
            bugBountyIndexer.indexVulnerabilityLabCurated(bugBountyConsumer);
            bugBountyIndexer.indexHackeroneCurated(bugBountyConsumer);
            generator.writeEndArray();
        }
    }

}
