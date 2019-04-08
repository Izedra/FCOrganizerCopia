package com.example.fcorganizer.pojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.net.URL

data class PersonajeP (
    @SerializedName("Achievements")
    @Expose
    val Achievements: List<Achievement>?,
    @SerializedName("Character")
    @Expose
    val Character: CharData?,
    @SerializedName("FreeCompany")
    @Expose
    val FreeCompany: FreeCompany?,
    @SerializedName("FreeCompanyMembers")
    @Expose
    val FreeCompanyMembers: List<Resultado>?,
    @SerializedName("Friends")
    @Expose
    val Friends: List<Resultado>?,
    @SerializedName("Info")
    @Expose
    val Info: Information?,
    @SerializedName("PvPTeam")
    @Expose
    val PvPTeam: PVPTeam?
)

    data class Achievement (
        @SerializedName("Date")
        @Expose
        val date: Int?,
        @SerializedName("ID")
        @Expose
        val ID: Int?
    )

    data class CharData(
        @SerializedName("ActiveClassJob")
        @Expose
        val ActiveClassJob: ActiveClassJob?,
        @SerializedName("Avatar")
        @Expose
        val Avatar: URL?,
        @SerializedName("Bio")
        @Expose
        val Bio: String?,
        @SerializedName("ClassJobs")
        @Expose
        val ClassJobs: ClassJobs?,
        @SerializedName("FreeCompanyId")
        @Expose
        val FreeCompanyId: String?,
        @SerializedName("GearSer")
        @Expose
        val GearSet: GearSet?,
        @SerializedName("Gender")
        @Expose
        val Gender: Int?,
        @SerializedName("GrandCompany")
        @Expose
        val GrandCompany: GrandCompany?,
        @SerializedName("GuardianDeity")
        @Expose
        val GuardianDeity: Int?,
        @SerializedName("ID")
        @Expose
        val ID: Int?,
        @SerializedName("Minions")
        @Expose
        val Minions: List<Int?>?,
        @SerializedName("Mounts")
        @Expose
        val Mounts: List<Int?>?,
        @SerializedName("Name")
        @Expose
        val Name: String?,
        @SerializedName("Nameday")
        @Expose
        val Nameday: String?,
        @SerializedName("ParseDate")
        @Expose
        val ParseDate: Int?,
        @SerializedName("Portrait")
        @Expose
        val Portrait: URL,
        @SerializedName("PvPTeamId")
        @Expose
        val PvPTeamId: Int?,
        @SerializedName("Race")
        @Expose
        val Race: Int?,
        @SerializedName("Server")
        @Expose
        val Server: String?,
        @SerializedName("Title")
        @Expose
        val Title: Int?,
        @SerializedName("Town")
        @Expose
        val Town: Int?,
        @SerializedName("Tribe")
        @Expose
        val Tribe: Int?
    )

        data class ActiveClassJob(
            @SerializedName("ClassID")
            @Expose
            val ClassID: Int?,
            @SerializedName("ExpLevel")
            @Expose
            val ExpLevel: Int?,
            @SerializedName("ExpLevelMax")
            @Expose
            val ExpLevelMax: Int?,
            @SerializedName("ExpLevelToGo")
            @Expose
            val ExpLevelToGo: Int?,
            @SerializedName("JobID")
            @Expose
            val JobID: Int?,
            @SerializedName("Level")
            @Expose
            val Level: Int?
        )

        data class ClassJobs(
            @SerializedName("Armorer")
            @Expose
            val armorer: ClassJob,
            @SerializedName("Goldsmith")
            @Expose
            val goldsmith: ClassJob,
            @SerializedName("UNK1")
            @Expose
            val unk1: ClassJob?,
            @SerializedName("UNK2")
            @Expose
            val unk2: ClassJob?,
            @SerializedName("Alchemist")
            @Expose
            val alchemist: ClassJob,
            @SerializedName("Culinarian")
            @Expose
            val culinarian: ClassJob,
            @SerializedName("Miner")
            @Expose
            val miner: ClassJob,
            @SerializedName("Botanist")
            @Expose
            val botanist: ClassJob,
            @SerializedName("UNK3")
            @Expose
            val unk3: ClassJob?,
            @SerializedName("Paladin")
            @Expose
            val paladin: ClassJob,
            @SerializedName("Summoner")
            @Expose
            val summoner: ClassJob,
            @SerializedName("Scholar")
            @Expose
            val scholar: ClassJob,
            @SerializedName("Ninja")
            @Expose
            val ninja: ClassJob,
            @SerializedName("Monk")
            @Expose
            val monk: ClassJob,
            @SerializedName("Machinist")
            @Expose
            val machinist: ClassJob,
            @SerializedName("Dark Knight")
            @Expose
            val dark_k: ClassJob,
            @SerializedName("Astrologian")
            @Expose
            val astrologian: ClassJob,
            @SerializedName("Samurai")
            @Expose
            val samurai: ClassJob,
            @SerializedName("Red Mage")
            @Expose
            val red_mage: ClassJob,
            @SerializedName("Blue Mage")
            @Expose
            val blue_mage: ClassJob,
            @SerializedName("Warrior")
            @Expose
            val warrior: ClassJob,
            @SerializedName("Dragoon")
            @Expose
            val dragoon: ClassJob,
            @SerializedName("Bard")
            @Expose
            val bard: ClassJob,
            @SerializedName("White Mage")
            @Expose
            val white_mage: ClassJob,
            @SerializedName("Black Mage")
            @Expose
            val black_mage: ClassJob,
            @SerializedName("Carpenter")
            @Expose
            val carpenter: ClassJob,
            @SerializedName("Blacksmith")
            @Expose
            val blacksmith: ClassJob
        )

                data class ClassJob(
                    @SerializedName("ClassID")
                    @Expose
                    val ClassID: Int?,
                    @SerializedName("ExpLevel")
                    @Expose
                    val ExpLevel: Int?,
                    @SerializedName("ExpLevelMax")
                    @Expose
                    val ExpLevelMax: Int?,
                    @SerializedName("ExpLevelToGo")
                    @Expose
                    val ExpLevelToGo: Int?,
                    @SerializedName("JobID")
                    @Expose
                    val JobID: Int?,
                    @SerializedName("Level")
                    @Expose
                    val Level: Int?
                )

        data class GearSet(
            @SerializedName("Attributes")
            @Expose
            val Attributes: Atributos,
            @SerializedName("ClassID")
            @Expose
            val ClassID: Int?,
            @SerializedName("Gear")
            @Expose
            val Gear: Gear?,
            @SerializedName("GearKey")
            @Expose
            val GearKey: String?,
            @SerializedName("JobID")
            @Expose
            val JobID: Int?,
            @SerializedName("Level")
            @Expose
            val Level: Int?
        )

                data class Atributos(
                    @SerializedName("Fuerza")
                    @Expose
                    val fuerza: Int,
                    @SerializedName("Destreza")
                    @Expose
                    val destreza: Int,
                    @SerializedName("Vitalidad")
                    @Expose
                    val vitalidad: Int,
                    @SerializedName("Inteligencia")
                    @Expose
                    val inteligencia: Int,
                    @SerializedName("Mente")
                    @Expose
                    val mente: Int,
                    @SerializedName("Critico")
                    @Expose
                    val critico: Int,
                    @SerializedName("Determinacion")
                    @Expose
                    val determinacion: Int,
                    @SerializedName("Directo")
                    @Expose
                    val directo: Int,
                    @SerializedName("Defensa")
                    @Expose
                    val defensa: Int,
                    @SerializedName("Defensa Magica")
                    @Expose
                    val defensa_m: Int,
                    @SerializedName("Ataque")
                    @Expose
                    val ataque: Int,
                    @SerializedName("Skill Speed")
                    @Expose
                    val velocidad_sk: Int,
                    @SerializedName("Ataque Magico")
                    @Expose
                    val ataque_m: Int,
                    @SerializedName("Curacion")
                    @Expose
                    val curacion: Int,
                    @SerializedName("Cast Speed")
                    @Expose
                    val velocidad_sp: Int,
                    @SerializedName("Tenacidad")
                    @Expose
                    val tenacidad: Int,
                    @SerializedName("Piedad")
                    @Expose
                    val piedad: Int
                )

                data class Gear(
                    val body: Pieza,
                    val bracelets: Pieza,
                    val earrings: Pieza,
                    val feet: Pieza,
                    val hands: Pieza,
                    val legs: Pieza,
                    val mainhand: Pieza,
                    val necklace: Pieza,
                    val ring1: Pieza,
                    val ring2: Pieza,
                    val soul: Pieza,
                    val waist: Pieza,
                    val gearKey: String,
                    val JobID: Int,
                    val Level: Int
                )

                        data class Pieza (
                            @SerializedName("Creator")
                            @Expose
                            val creator: Any?,
                            @SerializedName("Dye")
                            @Expose
                            val dye: Int?,
                            @SerializedName("ID")
                            @Expose
                            val id: Int?,
                            @SerializedName("Materia")
                            @Expose
                            val materia: List<Int?>?,
                            @SerializedName("Mirage")
                            @Expose
                            val mirage: Any?
                )

        data class GrandCompany (
            @SerializedName("NameID")
            @Expose
            val NameID: Int?,
            @SerializedName("RankID")
            @Expose
            val RankID: Int?
        )

    data class FreeCompany (
        @SerializedName("Active")
        val Active: String?,
        @SerializedName("ActiveMemberCount")
        val ActiveMemberCount: Int?,
        @SerializedName("Crest")
        @Expose
        val Crest: List<URL?>?,
        @SerializedName("Estate")
        @Expose
        val Estate: Estate?,
        @SerializedName("Focus")
        @Expose
        val Focus: List<Focus>?,
        @SerializedName("Formed")
        @Expose
        val Formed: Int?,
        @SerializedName("GrandCompany")
        @Expose
        val GrandCompany: String?,
        @SerializedName("ID")
        @Expose
        val ID: String?,
        @SerializedName("Name")
        @Expose
        val Name: String?,
        @SerializedName("ParseDate")
        @Expose
        val ParseDate: Int?,
        @SerializedName("Rank")
        @Expose
        val Rank: Int?,
        @SerializedName("Ranking")
        @Expose
        // Está mal escrito a proposito
        val Ranking: Rangking?,
        @SerializedName("Recruitment")
        @Expose
        val Recruitment: String?,
        @SerializedName("Reputation")
        @Expose
        val Reputation: Reputacion?,
        @SerializedName("Seeking")
        @Expose
        val Seeking: List<Buscando?>?,
        @SerializedName("Server")
        @Expose
        val Server: String?,
        @SerializedName("Slogan")
        @Expose
        val Slogan: String?,
        @SerializedName("Tag")
        @Expose
        val Tag: String?
    )

        data class Estate (
            val Greeting: String?,
            val Name: String?,
            val Plot: String?
        )

        data class Focus (
            val Icon: URL?,
            val Name: String?,
            val Status: Boolean?
        )

        data class Rangking (
            val Monthly: Int?,
            val Weekly: Int?
        )

        data class Reputacion (
            val Name: String?,
            val Progress: Int?,
            val Rank: String?
        )

        data class Buscando (
            val Icon: URL?,
            val Name: String?,
            val Status: Boolean?
        )

    data class Information (
        val State: Int?,
        val Updated: Int?
    )

    data class PVPTeam (
        val Pagination: Pagination?,
        val Profile: ProfileD?,
        val Results: List<Resultado?>?
    )

        data class Pagination (
            val Page: Int?,
            val PageNext: Int?,
            val PagePrev: Int?,
            val PageTotal: Int?,
            val Results: Int?,
            val ResultsPage: Int?,
            val ResultsPerPage: Int?,
            val ResultTotal: Int?
        )

        data class ProfileD (
            val Crest: List<URL?>?,
            val Name: String?,
            val Server: String?
        )

        data class Resultado (
            @SerializedName("Avatar")
            @Expose
            val Avatar: URL?,
            val FeastMatches: Int?,
            @SerializedName("ID")
            @Expose
            val ID: Int?,
            @SerializedName("Name")
            @Expose
            val Name: String?,
            val Rank: Any?,
            val RankIcon: URL?,
            @SerializedName("Server")
            @Expose
            val Server: String?
        )