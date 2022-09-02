package cache

import androidx.compose.runtime.mutableStateListOf
import entity.Skill
import entity.SkillType
import entity.SpiritAttributes

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

    val attrs = mutableListOf<SpiritAttributes>().apply {
        add(SpiritAttributes(1, "冰系"))
        add(SpiritAttributes(2, "恶魔系"))
        add(SpiritAttributes(3, "幽灵系"))
        add(SpiritAttributes(4, "土系"))
        add(SpiritAttributes(5, "水系"))
        add(SpiritAttributes(6, "石系"))
        add(SpiritAttributes(7, "普通系"))
        add(SpiritAttributes(8, "龙系"))
        add(SpiritAttributes(9, "火系"))
        add(SpiritAttributes(10, "毒系"))
        add(SpiritAttributes(11, "电系"))
        add(SpiritAttributes(12, "虫系"))
        add(SpiritAttributes(13, "草系"))
        add(SpiritAttributes(14, "武系"))
        add(SpiritAttributes(15, "机械系"))
        add(SpiritAttributes(16, "萌系"))
        add(SpiritAttributes(17, "翼系"))
        add(SpiritAttributes(18, "光系"))
        add(SpiritAttributes(19, "神草系"))
        add(SpiritAttributes(20, "神火系"))
        add(SpiritAttributes(21, "神水系"))
    }

    val attrsWithUnknow = mutableStateListOf<SpiritAttributes>().apply {
        add(SpiritAttributes(-1, "unknow"))
        addAll(attrs)
    }

    val skillType = mutableListOf<SkillType>().apply {
        add(SkillType(1, "物理"))
        add(SkillType(2, "魔法"))
        add(SkillType(3, "变化"))
    }
}