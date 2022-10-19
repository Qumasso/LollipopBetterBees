package net.lollipopmc.lollipopbetterbees.config;

import net.lollipopmc.lib.configurate.objectmapping.ConfigSerializable;
import net.lollipopmc.lib.configuration.holder.ConfigHolder;

import java.io.File;
import java.util.Arrays;
import java.util.List;

/*
message-if-home-exists:
  - '#123aFCУлей/гнездо пчелы находится на X: ${x}, Y: ${y}, Z: ${z}'
  - 'У неё ${stungMessage}, ${nectarMessage}'
  - 'Она ${flowerMessage}'
# Плейсхолдеры для message-if-home-exists: ${stungMessage}, ${nectarMessage}, ${flowerMessage}
message-if-home-doesnt-exist:
  - 'Улей/гнездо уничтожен'
  - 'У неё ${stungMessage}, ${nectarMessage}'
  - 'Она ${flowerMessage}'
# Следующие сообщения являются плейсами для двух предыдущих, разрешено лишь использование HEX-цветов
has-stung-message: 'есть жало'
no-stung-message: 'нету жала'
has-nectar-message: 'есть нектар'
no-nectar-message: 'нету нектара'
has-flower-message: 'выбрала цветок'
no-flower-message: 'не выбрала цветок'
 */
@ConfigSerializable
public class BeeConfigHolder extends ConfigHolder<BeeConfigHolder> {

    public List<String> messageIfHomeExists = Arrays.asList(
            "#123aFCУлей/гнездо пчелы находится на X: ${x}, Y: ${y}, Z: ${z}",
            "У неё ${stungMessage}, ${nectarMessage}",
            "Она ${flowerMessage}"
    );

    public List<String> messageIfHomeDoesntExist = Arrays.asList(
            "Улей/гнездо уничтожен",
            "У неё ${stungMessage}, ${nectarMessage}",
            "Она ${flowerMessage}"
    );

    public String hasStungMessage = "есть жало";

    public String noStungMessage = "нету жала";

    public String hasNectarMessage = "есть нектар";

    public String noNectarMessage = "нету нектара";

    public String hasFlowerMessage = "выбрала цветок";

    public String noFlowerMessage = "не выбрала цветок";

    public BeeConfigHolder(File baseFilePath) {
        super(baseFilePath);
    }

    public BeeConfigHolder() {
        this(null);
    }

}
