package com.chidemgames.protectthesurvivors.factories;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;
import com.badlogic.gdx.physics.box2d.ChainShape;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.World;
import com.chidemgames.protectthesurvivors.Constants;

public class PhysicsFactory {

	public static Body createBody(World world, BodyType bodyType,
			FixtureDef fixtureDef, Vector2 position) {
		BodyDef bodyDef = new BodyDef();
		bodyDef.type = bodyType;
		bodyDef.position.set(position);

		Body body = world.createBody(bodyDef);
		body.createFixture(fixtureDef);

		fixtureDef.shape.dispose();

		return body;
	}

	public static Shape createBoxShape(float halfWidth, float halfHeight, Vector2 center, float angle) {
		PolygonShape boxShape = new PolygonShape();
		boxShape.setAsBox(halfWidth, halfHeight, center, angle);
		
		return boxShape;
	}

	public static Shape createChainShape(Vector2[] vertices) {
		ChainShape chainShape = new ChainShape();
		chainShape.createChain(vertices);

		return chainShape;
	}

	public static CircleShape createCircleShape(float radius) {
		CircleShape circleShape = new CircleShape();
		circleShape.setRadius(radius);

		return circleShape;
	}

	public static Shape createPolygonShape(Vector2[] vertices) {
		PolygonShape polygonShape = new PolygonShape();
		polygonShape.set(vertices);

		return polygonShape;
	}

	public static Shape createTriangleShape(float halfWidth, float halfHeight) {
		PolygonShape triangleShape = new PolygonShape();
		triangleShape
				.set(new Vector2[] { new Vector2(-halfWidth, -halfHeight),
						new Vector2(0, halfHeight),
						new Vector2(halfWidth, -halfHeight) });

		return triangleShape;
	}

	public static FixtureDef createFixture(Shape shape, float density,
			float friction, float restitution, boolean isSensor) {
		FixtureDef fixtureDef = new FixtureDef();
		fixtureDef.shape = shape;
		fixtureDef.isSensor = isSensor;
		fixtureDef.density = density;
		fixtureDef.friction = friction;
		fixtureDef.restitution = restitution;

		return fixtureDef;
	}
	
	public static Body createConstructArea(World world, float viewportWidth,
			float viewportHeight, float offset){
		float halfWidth = viewportWidth / 4 - offset;
		float halfHeight = viewportHeight / 2 - offset;
		
		Vector2[] vertices = new Vector2[] {
			new Vector2(-halfWidth, -halfHeight),
			new Vector2(halfWidth, -halfHeight),
			new Vector2(halfWidth, (halfHeight * 2.5f)),
			new Vector2(-halfWidth, (halfHeight * 2.5f)),
			new Vector2(-halfWidth, -halfHeight)
		};
		
		Shape shape = createPolygonShape(vertices);
		FixtureDef fix = createFixture(shape, 1, 0.4f, 0, true);
		
		return createBody(world, BodyType.StaticBody, fix, new Vector2(0, 0));
	}

	public static Body createWalls(World world, float viewportWidth,
			float viewportHeight, float offset) {
			float halfWidth = viewportWidth / 2 - offset;
			float halfHeight = viewportHeight / 2 - offset;
		
		Vector2[] vertices = new Vector2[] {
			new Vector2(-(halfWidth + 3f), -halfHeight), 		
			new Vector2(halfWidth + 3f, -halfHeight),			
			new Vector2(halfWidth + 3f, -halfHeight + 3f),		
			new Vector2(halfWidth, -halfHeight + 3f),			
			new Vector2(halfWidth, (halfHeight * 2.5f)),		
			new Vector2(-halfWidth, (halfHeight * 2.5f)),		
			new Vector2(-halfWidth, -halfHeight + 3f),			
			new Vector2(-(halfWidth + 3f), -halfHeight + 3f),	
			new Vector2(-(halfWidth + 3f), -halfHeight) 		
		};
		
		Shape shape = createChainShape(vertices);
		FixtureDef fixtureDef = createFixture(shape, 1, 0.5f, 0, false);
		
		fixtureDef.filter.categoryBits = Constants.CATEGORY_WORLD_ENTITY;
		fixtureDef.filter.maskBits = Constants.MASK_WORLD_ENTITY;
		fixtureDef.filter.groupIndex = Constants.GROUP_WORLD_ENTITY;

		Body body = createBody(world, BodyType.StaticBody, fixtureDef, new Vector2(0, 0));
		body.setUserData("wall");
		return body;
	}

}