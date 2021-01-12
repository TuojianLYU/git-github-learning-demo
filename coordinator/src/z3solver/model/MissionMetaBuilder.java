package z3solver.model;

import com.microsoft.z3.Context;

public class MissionMetaBuilder {
    private Context ctx;
    private String filePath;
    private int numMaxFBs;
    private int numOfContainers;
    private int numOfFBs;
    private int[][] intensity;

    public MissionMetaBuilder setCtx(Context ctx) {
        this.ctx = ctx;
        return this;
    }

    public MissionMetaBuilder setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public MissionMetaBuilder setNumMaxFBs(int numMaxFBs) {
        this.numMaxFBs = numMaxFBs;
        return this;
    }

    public MissionMetaBuilder setNumOfContainers(int numOfContainers) {
        this.numOfContainers = numOfContainers;
        return this;
    }

    public MissionMetaBuilder setNumOfFBs(int numOfFBs) {
        this.numOfFBs = numOfFBs;
        return this;
    }

    public MissionMetaBuilder setIntensity(int[][] intensity) {
        this.intensity = intensity;
        return this;
    }

    public MissionMeta createMissionMeta() {
        return new MissionMeta(ctx, filePath, numMaxFBs, numOfContainers, numOfFBs, intensity);
    }
}