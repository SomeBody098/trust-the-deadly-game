package com.run.game.entity.npc;

import com.run.game.utils.exception.NotInitializedObjectException;
import com.run.game.utils.net.Language;
import com.run.game.utils.param.NpcParam;
import com.run.game.utils.param.ParamFactory;

public class NpcCreator {

    public static Npc createNpc(String npcName, Language language){
        NpcParam param = ParamFactory.getNpcParam(npcName);
        if (param == null) throw new NotInitializedObjectException("Param npc is not init.");

        EmotionNpc emotion = createEmotionNpc(param);
        BehaviorNpc behavior = createBehaviorNpc(param);

        return new Npc(emotion, behavior, language);
    }

    private static EmotionNpc createEmotionNpc(NpcParam param){
        return new EmotionNpc(
            param.MAX_LOYALTY,
            param.MAX_SADNESS,
            param.MAX_ANGER,
            param.initial_loyalty,
            param.initial_sadness,
            param.initial_anger
        );
    }

    private static BehaviorNpc createBehaviorNpc(NpcParam param) {
        return new BehaviorNpc(
            param.name,
            param.backstory
        );
    }
}
