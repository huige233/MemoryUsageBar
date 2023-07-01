package huige233.memoryusagebar.proxy;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;

import java.util.Objects;

public class ClientProxy extends CommonProxy {
    public void registerItemRenderer(Item item,int meta,String id)
    {
        ModelLoader.setCustomModelResourceLocation( item, meta, new ModelResourceLocation(Objects.requireNonNull(item.getRegistryName()), id ) );
    }


    public void preInit(FMLPostInitializationEvent event)
    {
        super.preInit( event );
    }


    public void init(FMLInitializationEvent event)
    {
        super.init(event);
    }
}
