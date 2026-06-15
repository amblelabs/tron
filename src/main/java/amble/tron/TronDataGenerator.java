package amble.tron;

import amble.tron.core.TronItemGroups;
import amble.tron.core.TronItems;
import dev.amble.lib.datagen.lang.AmbleLanguageProvider;
import dev.amble.lib.datagen.lang.LanguageType;
import net.fabricmc.fabric.api.datagen.v1.DataGeneratorEntrypoint;
import net.fabricmc.fabric.api.datagen.v1.FabricDataGenerator;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.minecraft.registry.RegistryWrapper;

import java.util.concurrent.CompletableFuture;

public class TronDataGenerator implements DataGeneratorEntrypoint {
	@Override
	public void onInitializeDataGenerator(FabricDataGenerator fabricDataGenerator) {
		FabricDataGenerator.Pack pack = fabricDataGenerator.createPack();
		generate_EN_US_Language(pack);
	}

	public void generate_EN_US_Language(FabricDataGenerator.Pack pack) {
		pack.addProvider(
				((output, registriesFuture) -> addEnglishTranslations(output, registriesFuture, LanguageType.EN_US))); // en_us
		// (English
		// US)
	}

	public AmbleLanguageProvider addEnglishTranslations(FabricDataOutput output,
                                                        CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture, LanguageType languageType) {
		AmbleLanguageProvider provider = new AmbleLanguageProvider(output, languageType);

		provider.translateItems(TronItems.class);
		provider.addTranslation(TronItemGroups.MAIN, "TRON: A Digital Frontier");

		return provider;
	}
}
