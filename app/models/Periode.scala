package models

import java.time.Instant

class Periode (
          // ces instant ne seront pas utiliser au travers de l'API, on peut les garder ? stp
          debut: Instant = Instant.now(),
          fin: Instant = Instant.now(),
        )
