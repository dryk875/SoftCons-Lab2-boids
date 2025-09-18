package boids
import cs214.{Vector2, BoidSequence}

def isInRadius(boid: Boid, otherBoids: BoidSequence, radius: Float): BoidSequence =
  otherBoids.filter(b => boid.position.distanceTo(b.position) < radius)

def avoidance(boid: Boid, otherBoids: BoidSequence): Vector2 =
  otherBoids
    .filter(b => boid.position != b.position)
    .mapVector2(b => (boid.position - b.position).normalized / boid.position.distanceTo(b.position))
    .sum

def cohesion(boid: Boid, otherBoids: BoidSequence): Vector2 = 
  val nB = otherBoids.length
  if nB == 0 then Vector2.Zero
  else otherBoids.mapVector2(b => b.position).sum / nB.toFloat - boid.position

def alignment(boid: Boid, otherBoids: BoidSequence): Vector2 = 
  val nB = otherBoids.length
  if nB == 0 then Vector2.Zero
  else otherBoids.mapVector2(b => b.velocity).sum / nB.toFloat - boid.velocity

def containmentX(boid: Boid, physics: Physics): Vector2 = 
  if boid.position.x < physics.limits.xmin then Vector2.UnitRight
  else if physics.limits.xmax < boid.position.x then Vector2.UnitLeft
  else Vector2.Zero

def containmentY(boid: Boid, physics: Physics): Vector2 = 
  if boid.position.y < physics.limits.ymin then Vector2.UnitDown
  else if physics.limits.ymax < boid.position.y then Vector2.UnitUp
  else Vector2.Zero

def containment(boid: Boid, physics: Physics): Vector2 = 
  containmentX(boid, physics) + containmentY(boid, physics)

def acceleration(boid: Boid, otherBoids: BoidSequence, physics: Physics): Vector2 = 
  avoidance(boid, isInRadius(boid, otherBoids, physics.avoidanceRadius)) * physics.avoidanceWeight
  + cohesion(boid, isInRadius(boid, otherBoids, physics.perceptionRadius)) * physics.cohesionWeight
  + alignment(boid, isInRadius(boid, otherBoids, physics.perceptionRadius)) * physics.alignmentWeight
  + containment(boid, physics) * physics.containmentWeight

def newVelocity(boid: Boid, otherBoids: BoidSequence, physics: Physics): Vector2 = 
  val calVelo = boid.velocity + acceleration(boid, otherBoids, physics)
  if calVelo.norm < physics.minimumSpeed then calVelo / calVelo.norm  * physics.minimumSpeed 
  else if calVelo.norm > physics.maximumSpeed then calVelo / calVelo.norm * physics.maximumSpeed 
  else calVelo

def newPosition(boid: Boid, otherBoids: BoidSequence, physics: Physics): Vector2 = 
  boid.position + boid.velocity

/** Returns all the given boids, one tick later */
def tickWorld(allBoids: BoidSequence, physics: Physics): BoidSequence =
  allBoids.mapBoid(boid => 
    val otherBoids = allBoids.filter(b => !b.equals(boid))
    boid.copy(newPosition(boid, otherBoids, physics), newVelocity(boid, otherBoids, physics)))
