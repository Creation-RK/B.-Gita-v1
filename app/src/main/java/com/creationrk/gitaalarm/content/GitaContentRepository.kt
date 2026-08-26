package com.creationrk.gitaalarm.content

import com.creationrk.gitaalarm.R

/** The first bundled, offline-only content pack. */
object GitaContentRepository {
    private val verses = listOf(
        Shloka("BG1.1", 1, 1,
            "धृतराष्ट्र उवाच ।\nधर्मक्षेत्रे कुरुक्षेत्रे समवेता युयुत्सवः ।\nमामकाः पाण्डवाश्चैव किमकुर्वत सञ्जय ॥",
            "Dhritarashtra said: O Sanjaya, what did my sons and the sons of Pandu do, assembled at Kurukshetra, the field of dharma, eager for battle?",
            "Dhritarashtra asks Sanjaya about the armies gathered for the great battle.", R.raw.bg_01_01),
        Shloka("BG1.2", 1, 2,
            "सञ्जय उवाच ।\nदृष्ट्वा तु पाण्डवानीकं व्यूढं दुर्योधनस्तदा ।\nआचार्यमुपसङ्गम्य राजा वचनमब्रवीत् ॥",
            "Sanjaya said: Seeing the Pandava army arranged in formation, King Duryodhana approached his teacher and spoke.",
            "Sanjaya describes Duryodhana's response on seeing the Pandava army.", R.raw.bg_01_02),
        Shloka("BG1.3", 1, 3,
            "पश्यैतां पाण्डुपुत्राणामाचार्य महतीं चमूम् ।\nव्यूढां द्रुपदपुत्रेण तव शिष्येण धीमता ॥",
            "Behold, teacher, this mighty army of the sons of Pandu, arranged by your wise disciple, the son of Drupada.",
            "Duryodhana draws his teacher's attention to the strength and order of the opposing army.", R.raw.bg_01_03),
        Shloka("BG1.4", 1, 4,
            "अत्र शूरा महेष्वासा भीमार्जुनसमा युधि ।\nयुयुधानो विराटश्च द्रुपदश्च महारथः ॥",
            "Here are heroic great bowmen, equal in battle to Bhima and Arjuna: Yuyudhana, Virata, and the great warrior Drupada.",
            "Duryodhana begins naming the formidable warriors on the Pandava side.", R.raw.bg_01_04)
    )

    fun all(): List<Shloka> = verses
    fun at(index: Int): Shloka = verses[index.coerceIn(0, verses.lastIndex)]
    fun nextIndex(index: Int): Int = (index + 1) % verses.size
}
