package com.example.fcorganizer.pojos

import java.net.URL

data class PersonajeP (
    val Achievements: List<Achievement?>?,
    val Character: CharData?,
    val FreeCompany: FreeCompany?,
    val FreeCompanyMembers: FreeCompanyMembers?,
    val Friends: List<Friend?>?,
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
        val ClassJobs: List<ClassJob?>?,
        val FreeCompanyId: Int?,
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

        data class ClassJob(
            val ClassID: Int?,
            val ExpLevel: Int?,
            val ExpLevelMax: Int?,
            val ExpLevelToGo: Int?,
            val JobID: Int?,
            val Level: Int?
        )

        data class GearSet(
            val Attributes: List<Int?>?,
            val ClassID: Int?,
            val Gear: List<Pieza?>?,
            val GearKey: String?,
            val JobID: Int?,
            val Level: Int?
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

    data class FreeCompanyMembers (
        val Avatar: URL?,
        val FeastMatches: Int?,
        val ID: Int?,
        val Name: String?,
        val Rank: String?,
        val RankIcon: URL?,
        val Server: String?
    )

    data class Friend (
        val Avatar: URL?,
        val FeastMatches: Int?,
        val ID: Int?,
        val Name: String?,
        val Rank: Int?,
        val RankIcon: URL?,
        val Server: String?
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