package com.run.game.entity.npc;

import com.run.game.utils.net.Language;

public class Npc {

    private final EmotionNpc emotion;
    private final BehaviorNpc behavior;

    private final Language language;

    public Npc(EmotionNpc emotion, BehaviorNpc behavior, Language language) {
        this.emotion = emotion;
        this.behavior = behavior;
        this.language = language;
    }

    public String getPrompt(String args){
        switch (language){
            case ENGLISH: createEnglishPrompt(args);
            case RUSSIAN: return createRussianPrompt(args);
            default: return createEnglishPrompt(args);
        }
    }

    private String createEnglishPrompt(String args){
        return String.format(
            "You are - %s You backstory: %s Here are the parameters of emotions: loyalty:%b, sadness:%b, anger:%b. If you want to express an emotion, write: [emotion] 'text', for example: '[anger] I hate you'. " +
                "Player's request: '%s'. Give him a response based on your characterization. Don't be verbose - try to give a response in 3 to 15 words.",
            behavior.getName(),
            behavior.getBackstory(),
            emotion.getLoyalty(),
            emotion.getSadness(),
            emotion.getAnger(),
            args
        );
    }

    private String createRussianPrompt(String args){
        return String.format(
            "Ты - %s Твоя предистория: %s Вот параметры эмоций: лояльность:%b, печаль:%b, злость:%b. Если ты хочешь выразить эмоцию то пиши: [эмоция] 'текст', Пример: '[злость] я ненавижу тебя'. " +
                "Запрос игрока: '%s'. Дайте ему ответ, основанный на вашей характеристике. Не будь многословен - посторайся дать ответ в 3 - 15 слов.",
            behavior.getName(),
            behavior.getBackstory(),
            emotion.getLoyalty(),
            emotion.getSadness(),
            emotion.getAnger(),
            args
        );
    }
}
