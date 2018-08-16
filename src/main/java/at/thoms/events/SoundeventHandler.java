package at.thoms.events;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

public final class SoundeventHandler {
	
	public static final SoundEvent record1 = getRegisteredSoundEvent("carpentercraft:record1");
	
	
			
			
			private static SoundEvent getRegisteredSoundEvent(String name)
	  {
	    return (SoundEvent)SoundEvent.REGISTRY.getObject(new ResourceLocation(name));
	  }			

}
