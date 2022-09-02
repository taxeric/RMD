package cache

import androidx.compose.runtime.mutableStateListOf
import entity.Skill

object LocalCache {

    var currentChooseSkill: Skill = Skill()
    val currentSpiritSkills = mutableStateListOf<Skill>()

    fun addSkillData(skill: Skill) {
        for (it in 0 until currentSpiritSkills.size) {
            if (currentSpiritSkills[it].id == skill.id) {
                return
            }
        }
        currentSpiritSkills.add(skill)
    }
}