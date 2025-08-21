package me.tolek.render;

import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.RenderPhase;
import net.minecraft.client.render.VertexFormat;
import net.minecraft.client.render.VertexFormats;

import java.util.OptionalDouble;

public class MflpRenderLayers {

    public static final RenderLayer.MultiPhase ESP_LINES =
            RenderLayer.of("mflp:esp_lines", VertexFormats.LINES,
                    VertexFormat.DrawMode.LINES, 1536, false, true,
                    RenderLayer.MultiPhaseParameters.builder()
                            .program(RenderLayer.LINES_PROGRAM)
                            .lineWidth(new RenderPhase.LineWidth(OptionalDouble.of(2)))
                            .layering(RenderLayer.VIEW_OFFSET_Z_LAYERING)
                            .transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
                            .target(RenderLayer.ITEM_ENTITY_TARGET)
                            .writeMaskState(RenderLayer.ALL_MASK)
                            .depthTest(RenderLayer.ALWAYS_DEPTH_TEST)
                            .cull(RenderLayer.DISABLE_CULLING).build(false));

    public static final RenderLayer.MultiPhase LINES =
            RenderLayer.of("mflp:lines", VertexFormats.LINES,
                    VertexFormat.DrawMode.LINES, 1536, false, true,
                    RenderLayer.MultiPhaseParameters.builder()
                            .program(RenderLayer.LINES_PROGRAM)
                            .lineWidth(new RenderPhase.LineWidth(OptionalDouble.of(2)))
                            .layering(RenderLayer.VIEW_OFFSET_Z_LAYERING)
                            .transparency(RenderLayer.TRANSLUCENT_TRANSPARENCY)
                            .target(RenderLayer.ITEM_ENTITY_TARGET)
                            .writeMaskState(RenderLayer.ALL_MASK)
                            .depthTest(RenderLayer.LEQUAL_DEPTH_TEST)
                            .cull(RenderLayer.DISABLE_CULLING).build(false));

    public static RenderLayer getLines(boolean depthTest) { return depthTest ? LINES : ESP_LINES; }

}
