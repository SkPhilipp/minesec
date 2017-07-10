package net.minesec.commands.index;

import com.fasterxml.jackson.core.JsonGenerator;
import net.minesec.commands.shared.Command;
import net.minesec.commands.shared.Context;

import java.io.IOException;
import java.util.function.Consumer;

/**
 * Copyright (c) 30/06/2017, MineSec. All rights reserved.
 */
public class IndexCommand implements Command {

    @Override
    public void execute(Context context, String... args) throws IOException {
        BugBountyIndexer bugBountyIndexer = new BugBountyIndexer();
        try (JsonGenerator generator = context.createGenerator()) {
            generator.writeStartArray();
            Consumer<BugBounty> bugBountyConsumer = bounty -> {
                try {
                    generator.writeObject(bounty);
                } catch (IOException e) {
                    e.printStackTrace();
                    System.exit(1);
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
