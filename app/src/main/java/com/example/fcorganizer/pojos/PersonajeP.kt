package com.example.fcorganizer.pojos

import java.net.URL

data class PersonajeP (
    val Achievements: List<Achievement>?,
    val Character: CharData?,
    val FreeCompany: FreeCompany?,
    val FreeCompanyMembers: List<Resultado>?,
    val Friends: List<Resultado>?,
    val Info: Information?,
    val PvPTeam: PVPTeam?
)

    data class Achievement (
        val date: Int?,
        val ID: Int?
    )

    data class CharData(
        val ActiveClassJob: ActiveClassJob?,
        val Avatar: URL?,
        val Bio: String?,
        val ClassJobs: ClassJobs?,
        val FreeCompanyId: String?,
        val GearSet: GearSet?,
        val Gender: Int?,
        val GrandCompany: GrandCompany?,
        val GuardianDeity: Int?,
        val ID: Int?,
        val Minions: List<Int?>?,
        val Mounts: List<Int?>?,
        val Name: String?,
        val Nameday: String?,
        val ParseDate: Int?,
        val Portrait: URL,
        val PvPTeamId: Int?,
        val Race: Int?,
        val Server: String?,
        val Title: Int?,
        val Town: Int?,
        val Tribe: Int?
    )

        data class ActiveClassJob(
            val ClassID: Int?,
            val ExpLevel: Int?,
            val ExpLevelMax: Int?,
            val ExpLevelToGo: Int?,
            val JobID: Int?,
            val Level: Int?
        )

        data class ClassJobs(
            val armorer: ClassJob,
            val goldsmith: ClassJob,
            val unk1: ClassJob?,
            val unk2: ClassJob?,
            val alchemist: ClassJob,
            val culinarian: ClassJob,
            val miner: ClassJob,
            val botanist: ClassJob,
            val unk3: ClassJob?,
            val paladin: ClassJob,
            val summoner: ClassJob,
            val scholar: ClassJob,
            val ninja: ClassJob,
            val monk: ClassJob,
            val machinist: ClassJob,
            val dark_k: ClassJob,
            val astrologian: ClassJob,
            val samurai: ClassJob,
            val red_mage: ClassJob,
            val blue_mage: ClassJob,
            val warrior: ClassJob,
            val dragoon: ClassJob,
            val bard: ClassJob,
            val white_mage: ClassJob,
            val black_mage: ClassJob,
            val carpenter: ClassJob,
            val blacksmith: ClassJob
        )

                data class ClassJob(
                    val ClassID: Int?,
                    val ExpLevel: Int?,
                    val ExpLevelMax: Int?,
                    val ExpLevelToGo: Int?,
                    val JobID: Int?,
                    val Level: Int?
                )

        data class GearSet(
            val Attributes: Atributos,
            val ClassID: Int?,
            val Gear: Gear?,
            val GearKey: String?,
            val JobID: Int?,
            val Level: Int?
        )

                data class Atributos(
                    val fuerza: Int,
                    val destreza: Int,
                    val vitalidad: Int,
                    val inteligencia: Int,
                    val mente: Int,
                    val critico: Int,
                    val determinacion: Int,
                    val directo: Int,
                    val defensa: Int,
                    val defensa_m: Int,
                    val ataque: Int,
                    val velocidad_sk: Int,
                    val ataque_m: Int,
                    val curacion: Int,
                    val velocidad_sp: Int,
                    val tenacidad: Int,
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
                    val Creator: Int?,
                    val Dye: Int?,
                    val ID: Int?,
                    val Materia: List<Int?>?,
                    val Mirage: Int?
                )

        data class GrandCompany (
            val NameID: Int?,
            val RankID: Int?
        )

    data class FreeCompany (
        val Active: String?,
        val ActiveMemberCount: Int?,
        val Crest: List<URL?>?,
        val Estate: Estate?,
        val Focus: List<Foc>?,
        val Formed: Int?,
        val GrandCompany: String?,
        val ID: String?,
        val Name: String?,
        val ParseDate: Int?,
        val Rank: Int?,
        // Está mal escrito a proposito
        val Ranking: Rangking?,
        val Recruitment: String?,
        val Reputation: Reputacion?,
        val Seeking: List<Buscando?>?,
        val Server: String?,
        val Slogan: String?,
        val Tag: String?
    )

        data class Estate (
            val Greeting: String?,
            val Name: String?,
            val Plot: String?
        )

        data class Foc (
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
            val Avatar: URL?,
            val FeastMatches: Int?,
            val ID: Int?,
            val Name: String?,
            val Rank: Any?,
            val RankIcon: URL?,
            val Server: String?
        )