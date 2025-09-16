package boids
import cs214.{Vector2, BoidSequence}

def boidsWithinRadius(thisBoid: Boid, boids: BoidSequence, radius: Float): BoidSequence =
  boids // TODO: modify this

/** Force pushing boids away from each other */
def avoidanceForce(thisBoid: Boid, boidsWithinAvoidanceRadius: BoidSequence): cs214.Vector2 =
  cs214.Vector2.Zero // TODO: modify this

/** Force pushing boids towards each other */
def cohesionForce(thisBoid: Boid, boidsWithinPerceptionRadius: BoidSequence): cs214.Vector2 =
  cs214.Vector2.Zero // TODO: modify this

/** Force pushing boids to align with the direction of their neighbors */
def alignmentForce(thisBoid: Boid, boidsWithinPerceptionRadius: BoidSequence): cs214.Vector2 =
  cs214.Vector2.Zero // TODO: modify this

/** Force keeping boids within simulation bounds */
def containmentForce(thisBoid: Boid, limits: BoundingBox): cs214.Vector2 =
  cs214.Vector2.Zero // TODO: modify this

def totalForce(thisBoid: Boid, allBoids: BoidSequence, physics: Physics): Vector2 =
  val withinPerceptionRadius = boidsWithinRadius(thisBoid, allBoids, physics.perceptionRadius)
  val cohere = cohesionForce(thisBoid, withinPerceptionRadius)
  val align = alignmentForce(thisBoid, withinPerceptionRadius)
  val withinAvoidanceRadius = boidsWithinRadius(thisBoid, withinPerceptionRadius, physics.avoidanceRadius)
  val avoid = avoidanceForce(thisBoid, withinAvoidanceRadius)
  val contain = containmentForce(thisBoid, physics.limits)
  val total =
    avoid * physics.avoidanceWeight +
      cohere * physics.cohesionWeight +
      align * physics.alignmentWeight +
      contain * physics.containmentWeight
  total

/** Returns the given boid, one tick later */
def tickBoid(thisBoid: Boid, allBoids: BoidSequence, physics: Physics): Boid =
  val acceleration = totalForce(thisBoid, allBoids, physics)
  Boid(thisBoid.position, thisBoid.velocity) // TODO: modify this

/** Returns all the given boids, one tick later */
def tickWorld(allBoids: BoidSequence, physics: Physics): BoidSequence =
  allBoids // TODO: modify this
