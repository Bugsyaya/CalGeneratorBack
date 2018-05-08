package models

import java.time.Instant

case class Calendrier (
                        debut: Instant,
                        fin: Instant,
                        formation: Formation,
                        lieu : Lieu,
                        cours : Seq[Cours]

                      )