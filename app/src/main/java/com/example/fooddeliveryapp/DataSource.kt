package com.example.fooddeliveryapp

import com.example.fooddeliveryapp.models.Restaurant

class DataSource{

    companion object{

        fun createDataSet(): ArrayList<Restaurant>{
            val list = ArrayList<Restaurant>()
            list.add(
                Restaurant(
                    "Tortilla Casa",
                    "You made it to the end of the course!\r\n\r\nNext we'll be building the REST API!",
                    "https://images.pexels.com/photos/461198/pexels-photo-461198.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
                    "House of Great Mexican Tortillas"
                )
            )
            list.add(
                Restaurant(
                    "Dizni Palačinkarnica",
                    "The REST API course is complete. You can find the videos here: https://codingwithmitch.com/courses/build-a-rest-api/.",
                    "https://images.pexels.com/photos/376464/pexels-photo-376464.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
                    "Absolutley Irresistible"
                )
            )

            list.add(
                Restaurant(
                    "Toster",
                    "Justin has been producing online courses for YouTube, Udemy, and his website CodingForEntrepreneurs.com for over 5 years.",
                    "https://images.pexels.com/photos/2271107/pexels-photo-2271107.jpeg?auto=compress&cs=tinysrgb&dpr=2&h=750&w=1260",
                    "Burgers, Beer and more"
                )
            )
            list.add(
                Restaurant(
                    "Freelance Android Developer (Vasiliy Zukanov)",
                    "Vasiliy has been a freelance android developer for several years. He also has some of the best android development courses I've had the pleasure of taking on Udemy.com.",
                    "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/freelance_android_dev_vasiliy_zukanov.png",
                    "Steven"
                )
            )
            list.add(
                Restaurant(
                    "Freelance Android Developer, Donn Felker",
                    "Freelancing as an Android developer with Donn Felker.\\r\\n\\r\\nDonn is also:\\r\\n\\r\\n1) Founder of caster.io\\r\\n\\r\\n2) Co-host of the fragmented podcast (fragmentedpodcast.com).",
                    "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/freelance_android_dev_donn_felker.png",
                    "Richelle"
                )
            )
            list.add(
                Restaurant(
                    "Work Life Balance for Software Developers",
                    "What kind of hobbies do software developers have? It sounds like many software developers don't have a lot of hobbies and choose to focus on work. Is that a good idea?",
                    "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/work_life_balance.png",
                    "Jessica"
                )
            )
            list.add(
                Restaurant(
                    "Full Stack Web Developer - Nicholas Olsen",
                    "In this podcast I interviewed the Fullsnack Developer (AKA Nicholas Olsen).\\r\\n\\r\\nNicholas is many things. What I mean by that is, he's good at many things.\\r\\n\\r\\n1. He’s an entrepreneur\\r\\n\\r\\n2. Web developer\\r\\n\\r\\n3. Artist\\r\\n\\r\\n4. Graphic designer\\r\\n\\r\\n5. Musician (drums)\\r\\n\\r\\n6. Professional BodyBuilder.",
                    "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/fullsnack_developer.png",
                    "Guy"
                )
            )
            list.add(
                Restaurant(
                    "Javascript Expert - Wes Bos",
                    "Interviewing a web developer, javascript expert, entrepreneur, freelancer, podcaster, and much more.",
                    "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/javascript_expert_wes_bos.png",
                    "Ruby"
                )
            )
            list.add(
                Restaurant(
                    "Senior Android Engineer - Kaushik Gopal",
                    "Kaushik Gopal is a Senior Android Engineer working in Silicon Valley.\r\n\r\nHe works as a Senior Staff engineer at Instacart.\r\n\r\nInstacart: https://www.instacart.com/",
                    "https://raw.githubusercontent.com/mitchtabian/Kotlin-RecyclerView-Example/json-data-source/app/src/main/res/drawable/senior_android_engineer_kaushik_gopal.png",
                    "mitch"
                )
            )
            return list
        }
    }
}