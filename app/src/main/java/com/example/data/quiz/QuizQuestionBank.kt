package com.example.data.quiz

import com.example.data.model.Question

object QuizQuestionBank {

    val GK_QUESTIONS = listOf(
        Question(
            id = 101,
            questionText = "Which is the largest river island in the world, located on the Brahmaputra River in Assam?",
            questionTextAs = "ব্ৰহ্মপুত্ৰ নদীত অৱস্থিত পৃথিৱীৰ সৰ্ববৃহৎ নদীদ্বীপটো কি?",
            options = ["Umananda (উমানন্দ)", "Majuli (মাজুলী)", "Dibru-Saikhowa (ডিব্ৰু-ছৈখোৱা)", "Srirangam (শ্ৰীৰংগম)"],
            correctIndex = 1,
            explanation = "Majuli is the largest river island in the world, declared as a district in 2016. Umananda is the smallest inhabited river island."
        ),
        Question(
            id = 102,
            questionText = "Who was the first President of Independent India?",
            questionTextAs = "স্বাধীন ভাৰতৰ প্ৰথম ৰাষ্ট্ৰপতি কোন আছিল?",
            options = ["Dr. S. Radhakrishnan", "Dr. Rajendra Prasad", "Dr. B. R. Ambedkar", "Jawaharlal Nehru"],
            correctIndex = 1,
            explanation = "Dr. Rajendra Prasad served as the first President of India from 1950 to 1962."
        ),
        Question(
            id = 103,
            questionText = "Which planet in the solar system is known as the 'Red Planet'?",
            questionTextAs = "সৌৰজগতৰ কোনটো গ্ৰহক 'ৰঙা গ্ৰহ' বুলি কোৱা হয়?",
            options = ["Venus (শুক্ৰ)", "Mars (মঙ্গল)", "Jupiter (বৃহস্পতি)", "Saturn (শনি)"],
            correctIndex = 1,
            explanation = "Mars appears reddish due to the high presence of iron oxide (rust) on its surface."
        ),
        Question(
            id = 104,
            questionText = "The highest civilian award of the Republic of India is:",
            questionTextAs = "ভাৰতবৰ্ষৰ সৰ্বোচ্চ অসামৰিক সন্মান কি?",
            options = ["Padma Vibhushan", "Bharat Ratna", "Param Vir Chakra", "Padma Bhushan"],
            correctIndex = 1,
            explanation = "Bharat Ratna is the highest civilian award of India, instituted in 1954."
        ),
        Question(
            id = 105,
            questionText = "In which year did the Olympic Games originate in ancient Greece?",
            questionTextAs = "প্ৰাচীন গ্ৰীচত কিমান খ্ৰীষ্টপূৰ্বত প্ৰথম অলিম্পিক খেল অনুষ্ঠিত হৈছিল?",
            options = ["776 BC", "393 AD", "1896 AD", "500 BC"],
            correctIndex = 0,
            explanation = "The ancient Olympic Games began in 776 BC in Olympia, Greece. The modern games started in Athens in 1896."
        )
    )

    val EVS_QUESTIONS = listOf(
        Question(
            id = 201,
            questionText = "Kaziranga National Park in Assam is world famous for which endangered species?",
            questionTextAs = "অসমৰ কাজিৰঙা ৰাষ্ট্ৰীয় উদ্যান কি বিলুপ্তপ্ৰায় প্ৰাণীৰ বাবে বিশ্ববিখ্যাত?",
            options = ["Royal Bengal Tiger", "Great Indian One-Horned Rhinoceros (এশিঙীয়া গঁড়)", "Golden Langur (সোণালী বান্দৰ)", "Hoolock Gibbon (হলৌ বান্দৰ)"],
            correctIndex = 1,
            explanation = "Kaziranga hosts two-thirds of the world's great one-horned rhinoceros population and is a UNESCO World Heritage Site."
        ),
        Question(
            id = 202,
            questionText = "Which layer of the atmosphere contains the Ozone Layer that absorbs harmful UV rays?",
            questionTextAs = "বায়ুমণ্ডলৰ কোনটো স্তৰত ক্ষতিকাৰক অতিবেঙুনীয়া ৰশ্মি শোষণ কৰা অ'জন স্তৰ থাকে?",
            options = ["Troposphere (ট্ৰপ'স্ফিয়াৰ)", "Stratosphere (ষ্ট্ৰেট'স্ফিয়াৰ)", "Mesosphere (মেছ'স্ফিয়াৰ)", "Thermosphere (থাৰ্ম'স্ফিয়াৰ)"],
            correctIndex = 1,
            explanation = "The Stratosphere contains the protective ozone layer (O3) between approximately 15 to 35 km altitude."
        ),
        Question(
            id = 203,
            questionText = "Which gas is the primary contributor to anthropogenic Global Warming / Greenhouse Effect?",
            questionTextAs = "মানৱসৃষ্ট গোলকীয় উত্তাপনৰ বাবে মূলতঃ কোনটো সেউজ গৃহ গেছ দায়ী?",
            options = ["Oxygen (O2)", "Carbon Dioxide (CO2)", "Nitrogen (N2)", "Argon (Ar)"],
            correctIndex = 1,
            explanation = "Carbon dioxide (CO2) from fossil fuels and deforestation is the main driver of global warming."
        ),
        Question(
            id = 204,
            questionText = "World Environment Day is observed globally every year on:",
            questionTextAs = "প্ৰতিবছৰে বিশ্ব পৰিৱেশ দিৱস কোন তাৰিখে পালন কৰা হয়?",
            options = ["22nd April", "5th June", "16th September", "1st December"],
            correctIndex = 1,
            explanation = "World Environment Day is celebrated on June 5th (instituted by UNEP in 1972 at the Stockholm Conference)."
        ),
        Question(
            id = 205,
            questionText = "Which National Park in Assam is famous for feral horses and white-winged wood duck?",
            questionTextAs = "অসমৰ কোনখন ৰাষ্ট্ৰীয় উদ্যান বনৰীয়া ঘোঁৰা (Feral Horse) আৰু দেওহাঁহৰ বাবে জনাজাত?",
            options = ["Manas", "Dibru-Saikhowa (ডিব্ৰু-ছৈখোৱা)", "Raimona", "Nameri"],
            correctIndex = 1,
            explanation = "Dibru-Saikhowa National Park in Tinsukia/Dibrugarh is known for wild feral horses and the rare White-Winged Wood Duck."
        )
    )

    val HISTORY_QUESTIONS = listOf(
        Question(
            id = 301,
            questionText = "Who was the legendary Ahom general who defeated the Mughal army in the Battle of Saraighat in 1671?",
            questionTextAs = "১৬৭১ চনৰ শৰাইঘাটৰ যুদ্ধত মোগল সেনাক পৰাস্ত কৰা আহোমৰ প্ৰখ্যাত সেনাপতিজন কোন আছিল?",
            options = ["Lachit Borphukan (লাচিত বৰফুকন)", "Bir Chilarai (বীৰ চিলাৰায়)", "Atan Burhagohain (আটান বুঢ়াগোহাঁই)", "Badan Chandra Borphukan"],
            correctIndex = 0,
            explanation = "General Lachit Borphukan led the Ahoms to victory against Ram Singh's Mughal forces on the Brahmaputra at Saraighat."
        ),
        Question(
            id = 302,
            questionText = "Who was the founder of the Ahom Kingdom in Assam in 1228 AD?",
            questionTextAs = "১২২৮ খ্ৰীষ্টাব্দত অসমত আহোম ৰাজ্যৰ প্ৰতিষ্ঠাতা কোন আছিল?",
            options = ["Chaolung Sukaphaa (চাওলুং চুকাফা)", "Suhungmung", "Rudra Singha", "Gadadhar Singha"],
            correctIndex = 0,
            explanation = "Chaolung Sukaphaa founded the Ahom dynasty in 1228 AD after crossing the Patkai mountains, ruling for nearly 600 years."
        ),
        Question(
            id = 303,
            questionText = "The Treaty of Yandabo, which ended the First Anglo-Burmese War and brought Assam under British rule, was signed in:",
            questionTextAs = "য়াণ্ডাবু সন্ধি কিমান চনত স্বাক্ষৰিত হৈছিল, যাৰ দ্বাৰা অসম ব্ৰিটিছৰ অধীনলৈ আহিছিল?",
            options = ["1826", "1857", "1836", "1894"],
            correctIndex = 0,
            explanation = "The Treaty of Yandabo was signed on 24 February 1826 between the British East India Company and the Kingdom of Burma."
        ),
        Question(
            id = 304,
            questionText = "The historic peasant uprising at Patharughat against British taxation in 1894 is located in which present district of Assam?",
            questionTextAs = "১৮৯৪ চনৰ ব্ৰিটিছ বিৰোধী ঐতিহাসিক পথৰুঘাটৰ ৰণ অসমৰ কোনখন জিলাত অৱস্থিত?",
            options = ["Darrang (দৰং)", "Nagaon (নগাঁও)", "Kamrup", "Sonitpur"],
            correctIndex = 0,
            explanation = "Patharughat is in Darrang district, where British troops fired on unarmed peasant protesters in 1894 ('Patharughator Ran')."
        ),
        Question(
            id = 305,
            questionText = "Who was the 14-year-old freedom fighter martyred while attempting to hoist the Indian National Flag at Gohpur Police Station in 1942?",
            questionTextAs = "১৯৪২ চনৰ ভাৰত ত্যাগ আন্দোলনত গহপুৰ থানাত জাতীয় পতাকা উত্তোলন কৰোঁতে শ্বহীদ হোৱা বীৰাঙ্গনা কোন আছিল?",
            options = ["Bhogeswari Phukanani", "Kanaklata Barua (কনকলতা বৰুৱা)", "Kushal Konwar", "Maniram Dewan"],
            correctIndex = 1,
            explanation = "Kanaklata Barua was shot dead by British police at Gohpur during the Quit India Movement on 20 September 1942."
        )
    )

    val POLITY_QUESTIONS = listOf(
        Question(
            id = 401,
            questionText = "Who is known as the 'Father of the Indian Constitution' and Chairman of the Drafting Committee?",
            questionTextAs = "ভাৰতীয় সংবিধানৰ খচৰা সমিতিৰ সভাপতি আৰু সংবিধানৰ জনক বুলি কাক জনা যায়?",
            options = ["Dr. Rajendra Prasad", "Dr. B. R. Ambedkar (ড° বি. আৰ. আম্বেদকাৰ)", "Sardar Vallabhbhai Patel", "B. N. Rau"],
            correctIndex = 1,
            explanation = "Dr. Bhimrao Ramji Ambedkar headed the Drafting Committee and is celebrated as the Father of the Constitution of India."
        ),
        Question(
            id = 402,
            questionText = "How many seats are there in the Assam Legislative Assembly (অসম বিধানসভা)?",
            questionTextAs = "অসম বিধানসভাত মুঠ বিধায়ক আসনৰ সংখ্যা কিমান?",
            options = ["126 seats", "14 seats", "7 seats", "140 seats"],
            correctIndex = 0,
            explanation = "The Assam Legislative Assembly consists of 126 elected Members of the Legislative Assembly (MLAs)."
        ),
        Question(
            id = 403,
            questionText = "Which Article of the Indian Constitution is termed as the 'Heart and Soul of the Constitution' by Dr. Ambedkar?",
            questionTextAs = "ড° আম্বেদকাৰে সংবিধানৰ কোনটো অনুচ্ছেদক 'সংবিধানৰ হৃদয় আৰু আত্মা' বুলি অভিহিত কৰিছিল?",
            options = ["Article 21 (Right to Life)", "Article 32 (Right to Constitutional Remedies)", "Article 14 (Equality before Law)", "Article 19 (Freedom of Speech)"],
            correctIndex = 1,
            explanation = "Article 32 guarantees the Right to Constitutional Remedies through Writs (Habeas Corpus, Mandamus, Quo Warranto, etc.)."
        ),
        Question(
            id = 404,
            questionText = "Which Constitutional Amendment Act granted Constitutional status to Panchayati Raj Institutions in 1992?",
            questionTextAs = "১৯৯২ চনৰ কোনটো সংবিধান সংশোধনীৰ জৰিয়তে পঞ্চায়তী ৰাজ ব্যৱস্থাক সাংবিধানিক মৰ্যাদা দিয়া হয়?",
            options = ["42nd Amendment", "44th Amendment", "73rd Amendment (৭৩তম সংশোধনী)", "86th Amendment"],
            correctIndex = 2,
            explanation = "The 73rd Constitutional Amendment Act of 1992 added Part IX and the 11th Schedule for Panchayati Raj Institutions."
        ),
        Question(
            id = 405,
            questionText = "The Gauhati High Court has territorial jurisdiction over how many North Eastern States currently?",
            questionTextAs = "বৰ্তমান গুৱাহাটী উচ্চ ন্যায়ালয়ৰ অধিকাৰক্ষেত্ৰ উত্তৰ-পূবৰ কেইখন ৰাজ্যলৈ বিস্তৃত?",
            options = ["4 States (Assam, Nagaland, Mizoram, Arunachal Pradesh)", "7 States", "3 States", "1 State"],
            correctIndex = 0,
            explanation = "Gauhati High Court exercises jurisdiction over 4 states: Assam, Nagaland, Mizoram, and Arunachal Pradesh."
        )
    )

    val CURRENT_AFFAIRS_QUESTIONS = listOf(
        Question(
            id = 501,
            questionText = "Which historic burial mounds of the Ahom dynasty was inscribed as India's 43rd UNESCO World Heritage Site in July 2024?",
            questionTextAs = "২০২৪ চনৰ জুলাই মাহত ইউনেস্কোৰ বিশ্ব ঐতিহ্য ক্ষেত্ৰৰ মৰ্যাদা লাভ কৰা আহোম যুগৰ ঐতিহাসিক স্থানখন কি?",
            options = ["Rang Ghar (ৰংঘৰ)", "Charaideo Moidams (চৰাইদেউ মৈদাম)", "Talatal Ghar (তলাতল ঘৰ)", "Garhgaon Palace"],
            correctIndex = 1,
            explanation = "Moidams – the Mound-Burial System of the Ahom Dynasty at Charaideo, Assam was inscribed as a UNESCO World Heritage Site."
        ),
        Question(
            id = 502,
            questionText = "At which location in Assam is the prestigious ₹27,000 Crore Tata Semiconductor Assembly and Test facility being set up?",
            questionTextAs = "অসমৰ কোন স্থানত ২৭,০০০ কোটি টকা ব্যয় সাপেক্ষে টাটা ছেমিকণ্ডাক্টৰ উদ্যোগ নিৰ্মাণ কৰা হৈছে?",
            options = ["Guwahati", "Jagiroad (জাগীৰোড, মৰিগাঁও)", "Dibrugarh", "Silchar"],
            correctIndex = 1,
            explanation = "Tata Semiconductor Assembly and Test facility (OSAT) is established at Jagiroad in Morigaon district of Assam."
        ),
        Question(
            id = 503,
            questionText = "What is the primary objective of the 'Nijut Moina' Scheme launched by the Assam Government?",
            questionTextAs = "অসম চৰকাৰে আৰম্ভ কৰা 'নিযুত মইনা' আঁচনিৰ মূল উদ্দেশ্য কি?",
            options = ["Promotion of Electric Vehicles", "Financial stipend for girl students in HS, Degree & PG to eliminate child marriage", "Farmer pension", "Free sports kit distribution"],
            correctIndex = 1,
            explanation = "Nijut Moina provides monthly financial grants to girl students pursuing Higher Secondary, Degree, and PG courses to curb child marriage."
        ),
        Question(
            id = 504,
            questionText = "Which citrus fruit of Assam was officially declared as the 'State Fruit' of Assam in 2024?",
            questionTextAs = "২০২৪ চনত অসম চৰকাৰে কোনটো টেঙা ফলক 'ৰাজ্যিক ফল' হিচাপে ঘোষণা কৰে?",
            options = ["Assam Lemon / Kaji Nemu (কাজী নেমু)", "Gol Nemu", "Valencia Orange", "Wood Apple (বেল)"],
            correctIndex = 0,
            explanation = "Kaji Nemu (Citrus limon), which already holds a GI Tag, was officially designated as the State Fruit of Assam."
        ),
        Question(
            id = 505,
            questionText = "Under the 'Orunodoi 3.0' welfare scheme in Assam, what is the monthly financial assistance disbursed to eligible women beneficiaries?",
            questionTextAs = "অসমৰ 'অৰুণোদয় ৩.০' আঁচনিৰ অধীনত যোগ্য মহিলা হিতাধিকাৰীক মাহিলি কিমান টকাৰ আৰ্থিক সাহাৰ্য প্ৰদান কৰা হয়?",
            options = ["₹1000", "₹1250", "₹1400", "₹2000"],
            correctIndex = 1,
            explanation = "Orunodoi scheme provides Direct Benefit Transfer of ₹1,250 every month into the bank accounts of women beneficiaries."
        )
    )

    val ADRE_3_PRACTICE_QUESTIONS = listOf(
        Question(
            id = 601,
            questionText = "[ADRE 3.0 Social Studies] During whose reign in Kamarupa did the famous Chinese pilgrim Hiuen Tsang visit Assam in 643 AD?",
            questionTextAs = "[ADRE ৩.০ সমাজ অধ্যয়ন] ৬৪৩ খ্ৰীষ্টাব্দত চীনা পৰিব্ৰাজক হিউৱেন চাঙে কামৰূপলৈ আহোঁতে কামৰূপৰ ৰজা কোন আছিল?",
            options = ["Pushyavarman", "Kumar Bhaskaravarman (কুমাৰ ভাস্কৰবৰ্মন)", "Narakasura", "Brahmapala"],
            correctIndex = 1,
            explanation = "Hiuen Tsang visited Kamarupa upon the invitation of King Kumar Bhaskaravarman of the Varman dynasty during Harshavardhana's reign."
        ),
        Question(
            id = 602,
            questionText = "[ADRE 3.0 General Awareness] Who was the first Chief Minister (Prime Minister of Assam before independence) of Assam?",
            questionTextAs = "[ADRE ৩.০ সাধাৰণ জ্ঞান] অসমৰ প্ৰথমগৰাকী মুখ্যমন্ত্ৰী (স্বাধীনতাৰ পূৰ্বে প্ৰধানমন্ত্ৰী) কোন আছিল?",
            options = ["Bimala Prasad Chaliha", "Lokapriya Gopinath Bordoloi (লোকপ্ৰিয় গোপীনাথ বৰদলৈ)", "Bishnuram Medhi", "Sir Syed Muhammad Saadulla"],
            correctIndex = 1,
            explanation = "Lokapriya Gopinath Bordoloi was the first Premier/Chief Minister of independent Assam, honored with Bharat Ratna posthumously in 1999."
        ),
        Question(
            id = 603,
            questionText = "[ADRE 3.0 Reasoning] Complete the series: 3, 7, 15, 31, 63, ?",
            questionTextAs = "[ADRE ৩.০ যুক্তিবিদ্যা] শ্ৰেণীটো সম্পূৰ্ণ কৰক: ৩, ৭, ১৫, ৩১, ৬৩, ?",
            options = ["125", "127", "126", "131"],
            correctIndex = 1,
            explanation = "Pattern: (x * 2) + 1 => 3*2+1=7, 7*2+1=15, 15*2+1=31, 31*2+1=63, 63*2+1 = 127."
        ),
        Question(
            id = 604,
            questionText = "[ADRE 3.0 English Language] Choose the correct antonym of the word 'BENEVOLENT':",
            questionTextAs = "[ADRE ৩.০ ইংৰাজী] 'BENEVOLENT' (দয়ালু/পৰোপকাৰী) শব্দটোৰ বিপৰীত শব্দ বাছক:",
            options = ["Generous", "Malevolent (হিংসুক/অপকাৰী)", "Kind", "Sympathetic"],
            correctIndex = 1,
            explanation = "'Benevolent' means kind and helpful. Its exact antonym is 'Malevolent' (wishing or doing evil to others)."
        ),
        Question(
            id = 605,
            questionText = "[ADRE 3.0 Assamese Grammar] 'প্ৰত্যুপকাৰ' শব্দটোৰ শুদ্ধ সন্ধি ভাঙনি হ'ল:",
            questionTextAs = "[ADRE ৩.০ অসমীয়া ব্যাকৰণ] 'প্ৰত্যুপকাৰ' শব্দটোৰ শুদ্ধ সন্ধি ভাঙনি কি?",
            options = ["প্ৰতি + উপকাৰ", "প্ৰত্য + উপকাৰ", "প্ৰত + উপকাৰ", "প্ৰতি + অপকাৰ"],
            correctIndex = 0,
            explanation = "য-কাৰ সন্ধি নিয়ম: ই/ঈ + উ = ইউ (প্ৰতি + উপকাৰ = প্ৰত্যুপকাৰ)।"
        ),
        Question(
            id = 606,
            questionText = "[ADRE 3.0 Assam Geography] The longest road bridge in India, Dhola-Sadiya Bridge (Bhupen Hazarika Setu), connects Assam with which state?",
            questionTextAs = "[ADRE ৩.০ অসম ভূগোল] ভাৰতৰ সৰ্বোচ্চ দৈৰ্ঘ্যৰ নদী দলং ভূপেন হাজৰিকা সেতুৱে অসমক কোনখন ৰাজ্যৰ সৈতে সংযোগ কৰে?",
            options = ["Nagaland", "Arunachal Pradesh (অৰুণাচল প্ৰদেশ)", "Meghalaya", "Manipur"],
            correctIndex = 1,
            explanation = "The 9.15 km Bhupen Hazarika Setu on Lohit river connects Dhola in Assam to Sadiya near Arunachal Pradesh border."
        ),
        Question(
            id = 607,
            questionText = "[ADRE 3.0 Quantitative Aptitude] If the cost price of 12 pens is equal to the selling price of 8 pens, find the gain percentage:",
            questionTextAs = "[ADRE ৩.০ গণিত] ১২ টা কলমৰ ক্ৰয়মূল্য যদি ৮ টা কলমৰ বিক্ৰীমূল্যৰ সমান হয়, তেন্তে লাভৰ শতাংশ কিমান?",
            options = ["25%", "33.33%", "50%", "40%"],
            correctIndex = 2,
            explanation = "Let CP of 1 pen = ₹1. CP of 8 pens = ₹8, SP of 8 pens = CP of 12 pens = ₹12. Gain = 12 - 8 = ₹4. Gain% = (4/8)*100 = 50%."
        ),
        Question(
            id = 608,
            questionText = "[ADRE 3.0 General Science] Which vitamin is water-soluble and known as Ascorbic Acid, preventing Scurvy?",
            questionTextAs = "[ADRE ৩.০ সাধাৰণ বিজ্ঞান] পানীত দ্ৰৱণীয় কোনটো ভিটামিনক এছকৰ্বিক এচিড বুলি কোৱা হয়, যাৰ অভাৱত স্কাৰ্ভি ৰোগ হয়?",
            options = ["Vitamin A", "Vitamin C (ভিটামিন C)", "Vitamin D", "Vitamin K"],
            correctIndex = 1,
            explanation = "Vitamin C (Ascorbic Acid) is water soluble and deficiency causes bleeding gums and scurvy."
        )
    )

    fun getQuestionsBySubject(subject: String): List<Question> {
        return when (subject.lowercase()) {
            "gk", "general knowledge" -> GK_QUESTIONS
            "evs", "environmental studies", "environment" -> EVS_QUESTIONS
            "history", "assam history" -> HISTORY_QUESTIONS
            "polity", "political science" -> POLITY_QUESTIONS
            "current affairs", "assam current affairs" -> CURRENT_AFFAIRS_QUESTIONS
            "adre", "adre 3.0", "adre exam 3.0" -> ADRE_3_PRACTICE_QUESTIONS
            else -> GK_QUESTIONS + HISTORY_QUESTIONS + CURRENT_AFFAIRS_QUESTIONS + ADRE_3_PRACTICE_QUESTIONS
        }
    }

    fun getAllQuestions(): List<Question> {
        return GK_QUESTIONS + EVS_QUESTIONS + HISTORY_QUESTIONS + POLITY_QUESTIONS + CURRENT_AFFAIRS_QUESTIONS + ADRE_3_PRACTICE_QUESTIONS
    }
}
