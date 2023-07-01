package huige233.memoryusagebar.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class MemoryHUdREnder {
    @SubscribeEvent
    public static void render(RenderGameOverlayEvent.Pre event) {
        if (event.getType() == RenderGameOverlayEvent.ElementType.ALL) {
            FontRenderer fontRenderer = Minecraft.getMinecraft().fontRenderer;
            Minecraft mc = Minecraft.getMinecraft();
            //get scale resolution
            ScaledResolution scaledResolution = new ScaledResolution(mc);
            int y = scaledResolution.getScaledHeight() - 15;
            Runtime runtime = Runtime.getRuntime();
            long totalMemory = runtime.totalMemory()-runtime.freeMemory();
            long maxMemory = runtime.maxMemory();
            float totalMemoryPercent = (float) totalMemory / (float) maxMemory;

            GlStateManager.disableBlend();
            GlStateManager.disableAlpha();
            Tessellator tessellator = Tessellator.getInstance();
            BufferBuilder bufferbuilder = tessellator.getBuffer();
            int i = (int) (totalMemoryPercent * 100);//←这里是计算动态的宽度
            int j = getHealthBarColor(totalMemory, maxMemory);//这里是计算颜色
            draw(bufferbuilder, 2, y, 100, 10, 0, 0, 0, 502);//👈这里是底下那层灰色
            draw(bufferbuilder, 1, y, i, 10, j >> 16 & 255, j >> 8 & 255, j & 255, 255);
            String memoryUsagePercentage = String.format("%.2f%%", (double)totalMemory / (double)runtime.maxMemory() * 100.0);
            fontRenderer.drawString(memoryUsagePercentage, i, y-15, 0xF0F0F0, false);
            GlStateManager.enableBlend();
            GlStateManager.enableAlpha();
        }
    }

    private static int getHealthBarColor(long usedMemory, long maxMemory) {
        float percentage = (float) usedMemory / (float) maxMemory;
        if (percentage < 0.2) {
            return 0xFF00FF00; // 绿色 0xFF00FF00
        } else if (percentage < 0.5) {
            return 0xFFFF8000; // 橙色 0xFFFF8000
        } else {
            return 0xFFFF0000; // 红色 0xFFFF0000
        }
    }

    public static void drawTexturedModalRect(int x, int y, int textureX, int textureY, int width, int height) {
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(x, y, textureX, textureY, width, height);
    }


    private static void draw(BufferBuilder renderer, int x, int y, int width, int height, int red, int green, int blue, int alpha) {
        renderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
        renderer.pos((double) (x + 0), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double) (x + 0), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double) (x + width), (double) (y + height), 0.0D).color(red, green, blue, alpha).endVertex();
        renderer.pos((double) (x + width), (double) (y + 0), 0.0D).color(red, green, blue, alpha).endVertex();
        Tessellator.getInstance().draw();
    }
}
