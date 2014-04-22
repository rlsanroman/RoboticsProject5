/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author rosbelsanroman
 */

import java.awt.Color;
import java.awt.Point;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.swing.JLabel;
import javax.swing.JPanel;

import java.awt.Rectangle;
import java.util.Collections;
import java.util.Vector;


public class MainWindow extends javax.swing.JFrame {

	JPanel panel;
	JLabel label;
	int numLeftClicks = 0;
	int numRightClicks = 0;
	Point start = new Point(0,0), end = new Point(0,0);
	Rectangle[] rectangles = new Rectangle[3];
	Vector<Line2D> lines = new Vector<Line2D>();
	Vector<Position> midpoints = new Vector<Position>();
	ArrayList<Vertex> vertices = new ArrayList<Vertex>();
	ArrayList<Line2D> connections = new ArrayList<Line2D>();
    /**
     * Creates new form MainWindow
     */
    
	public MainWindow() {
        initComponents();
        
        //this.setTitle("Cell Decomposition");
    }
	
	public void getAdjacentMidPoints()
	{
		vertices.clear();
		for(Position pos : midpoints)
		{
			if(pos.x > 500 || pos.y > 500)
				continue;
			if(pos.equals(start))
				System.out.println("HI");
			if(pos.equals(end))
				System.out.println("HI");
			ArrayList<Position> neighbors = pos.findLeftRightPoints(midpoints);
			Vertex v = new Vertex(pos);
			ArrayList<Edge> edges = new ArrayList<Edge>();
			for(Position neighbor : neighbors)
			{
				Vertex v1 = new Vertex(neighbor);
				Line2D line = new Line2D.Double(new Point2D.Double(v.point.x,v.point.y), new Point2D.Double((double)v1.point.x,(double)v1.point.y));
				boolean intersects = false;
				for(Rectangle rec : this.rectangles)
				{
					if(rec.intersectsLine(line))
						intersects = true;
				}
				if(!intersects)
				{
					edges.add(new Edge(v1,Position.getDistance(v.point, v1.point)));
					connections.add(line);
				}
			}
			v.adjacencies = edges;
			vertices.add(v);
		}
	}

	public void drawCells(){
		Graphics2D g = (Graphics2D) canvasPanel.getGraphics();
		g.setColor(Color.LIGHT_GRAY);
		//int rec = 1;
		int MAXX = 0;
		int MINX = 500;
		int MAXY = 0;
		int MINY = 500;
		canvasPanel.removeAll(); //clear previous rectangles
		Rectangle bottom = null;
		Rectangle top = null;
		Rectangle middle = null;
		for(Rectangle rectangle : rectangles) //get the top most Rectangle/ bottom most Rectangles
		{
			int maxX = (int)rectangle.getMaxX();
			int maxY = (int)rectangle.getMaxY();
			int minX = (int)rectangle.getMinX();
			int minY = (int)rectangle.getMinY();
			
			// Left line 1
			 double x1 = rectangle.getMinX();
			 double y1 = 0;
			 double x2 = rectangle.getMinX();
			 double y2 = rectangle.getMinY();
			 
			 midpoints.add(new Position((int)(x1+x2)/2,(int)(y1+y2)/2));

			 // Left line 2
			 double x3 = rectangle.getMinX();
			 double y3 = rectangle.getMaxY();
			 double x4 = rectangle.getMinX();
			 double y4 = 500;
			 
			 //midpoints.add(new Position((int)(x3+x4)/2,(int)(y3+y4)/2));
			 
			//Left lines
			lines.add(new Line2D.Double(x1,0,x2,y2));
			lines.add(new Line2D.Double(x3,y3,x4,y4));
			 
			// Right line 1
			 double x5 = rectangle.getMaxX();
			 double y5 = 0;
			 double x6 = rectangle.getMaxX();
			 double y6 = rectangle.getMinY();
			 
			 //midpoints.add();

			 // Right line 2
			 double x7 = rectangle.getMaxX();
			 double y7 = rectangle.getMaxY();
			 double x8 = rectangle.getMaxX();
			 double y8 = 500; 
			 
			 //midpoints.add(new Position((int)(x7+x8)/2,(int)(y7+y8)/2));
			 			
			//Right lines
			lines.add(new Line2D.Double(x5,y5,x6,y6));
			lines.add(new Line2D.Double(x7,y7,x8,y8));
			
			if(maxX > MAXX) 
			{
				MAXX = maxX;
			}
			if(minX < MINX)
			{
				MINX = minX;
			}
			if(maxY > MAXY)
			{
				MAXY = maxY;
				bottom = rectangle;
			}
			if(minY < MINY)
			{
				MINY = minY;
				top = rectangle;
			}
		}
		for(Rectangle rectangle : rectangles)
		{
			if(rectangle != top && rectangle != bottom){
				middle = rectangle;
			}
		}
		
		for(Rectangle rectangle : rectangles)
		{
			int maxX = (int)rectangle.getMaxX();
			int maxY = (int)rectangle.getMaxY();
			int minX = (int)rectangle.getMinX();
			int minY = (int)rectangle.getMinY();
			
			//System.out.println("Rectangle " + rec++ + ": ");
			//System.out.println("Max X = " + maxX);
			//System.out.println("Max Y = " + maxY);
			//System.out.println("Min X = " + minX);
			//System.out.println("Min Y = " + minY);
			
			//g.drawLine(0,maxY,canvasPanel.getSize().width,maxY); //horizontal
			//g.drawLine(0,minY,canvasPanel.getSize().width,minY); //horizontal
			
			//newRectangles.addElement(new Rectangle(minX,0,minX,canvasPanel.getSize().height)); //Something 
			//newRectangles.addElement(new Rectangle(minX,0,minX,canvasPanel.getSize().height));
			//g.drawLine(minX,0,minX,canvasPanel.getSize().height); //vertical
			//g.drawLine(maxX,0,maxX,canvasPanel.getSize().height); //vertical
			
			//Generate Points:
			LocPoint toppoint = new LocPoint((maxX + minX) / 2, (minY + 0) / 2);
			LocPoint bottompoint = new LocPoint((maxX + minX) / 2, (maxY + (int)canvasPanel.getSize().getHeight()) / 2);
			//toppoint.drawPoint(g);
			//bottompoint.drawPoint(g);
			
			//NOT WORKING YET
			Line2D righttop = new Line2D.Float((float)maxX, minY, (float)maxX, 0);
			Line2D rightbottom = new Line2D.Float((float)maxX, maxY, (float)maxX, 500);
			Line2D leftbottom = new Line2D.Float((float)minX, maxY, (float)minX, 500);
			Line2D lefttop = new Line2D.Float((float)minX, minY, (float)minX, 0);
			lines.addElement(righttop);
			lines.addElement(rightbottom);
			lines.addElement(lefttop);
			lines.addElement(leftbottom);
			
			//Left Line
			if(lefttop.intersects(top) && rectangle != top){ //intersect w top
				if(lefttop.intersects(middle) && rectangle != middle){ //intersects with both middle and top
					g.drawLine((int)lefttop.getX1(), (int)rectangle.getMinY() ,(int)lefttop.getX2(), (int)middle.getMaxY());
					lefttop.setLine(lefttop.getX1(), rectangle.getMinY(), lefttop.getX2(), middle.getMaxY());
				}
				else{
					g.drawLine((int)lefttop.getX1(), (int)rectangle.getMinY() ,(int)lefttop.getX2(), (int)top.getMaxY());
					lefttop.setLine(lefttop.getX1(), rectangle.getMinY(), lefttop.getX2(), top.getMaxY());
				}
			}
			else{
				//Rectangle newrectangle = new Rectangle((int)lefttop.getX1(), (int)top.getMaxY(), (int)(top.getMaxX() - lefttop.getX1()), (int)(rectangle.getMinY() - top.getMaxY()));
				if(lefttop.intersects(middle) && rectangle != middle){ //intersects with both middle and top
					g.drawLine((int)lefttop.getX1(), (int)rectangle.getMinY() ,(int)lefttop.getX2(), (int)middle.getMaxY());
					lefttop.setLine(lefttop.getX1(), rectangle.getMinY(), lefttop.getX2(), middle.getMaxY());
				}
				else{
					g.drawLine((int)lefttop.getX1(),(int) lefttop.getY1(),(int)lefttop.getX2(),(int)lefttop.getY2());
					lefttop.setLine(lefttop.getX1(), lefttop.getY1(), lefttop.getX2(), lefttop.getY2());
				}
			}
			if(leftbottom.intersects(bottom) && rectangle != bottom){ //intersect w bottom
				if(leftbottom.intersects(middle) && rectangle != middle){ //intersects with both middle and top
					g.drawLine((int)leftbottom.getX1(),(int) leftbottom.getY1(),(int)leftbottom.getX2(),(int)middle.getMinY());
					leftbottom.setLine(leftbottom.getX1(), leftbottom.getY1(), leftbottom.getX2(), middle.getMinY());
				}
				else{
					g.drawLine((int)leftbottom.getX1(),(int) leftbottom.getY1(),(int)leftbottom.getX2(),(int)bottom.getMinY());
					leftbottom.setLine(leftbottom.getX1(), leftbottom.getY1(), leftbottom.getX2(), bottom.getMinY());
				}
			}
			else
				if(leftbottom.intersects(middle) && rectangle != middle){ //intersects with both middle and top
					g.drawLine((int)leftbottom.getX1(),(int) leftbottom.getY1(),(int)leftbottom.getX2(),(int)middle.getMinY());
					leftbottom.setLine(leftbottom.getX1(), leftbottom.getY1(), leftbottom.getX2(), middle.getMinY());
				}
				else{
					g.drawLine((int)leftbottom.getX1(),(int)leftbottom.getY1(),(int)leftbottom.getX2(),(int)leftbottom.getY2());
					leftbottom.setLine(leftbottom.getX1(), leftbottom.getY1(), leftbottom.getX2(), leftbottom.getY2());
				}
			//Right Line
			if(righttop.intersects(top) && rectangle != top) //intersect w top
				if(righttop.intersects(middle) && rectangle != middle){ //intersects with both middle and top
					g.drawLine((int)righttop.getX1(), (int)rectangle.getMinY() ,(int)righttop.getX2(), (int)middle.getMaxY());
					righttop.setLine(righttop.getX1(),rectangle.getMinY(),righttop.getX2(),middle.getMaxY());
				}
				else{
					g.drawLine((int)righttop.getX1(), (int)rectangle.getMinY() ,(int)righttop.getX2(), (int)top.getMaxY());
					righttop.setLine(righttop.getX1(),rectangle.getMinY(),righttop.getX2(),top.getMaxY());
				}
			else
				if(righttop.intersects(middle) && rectangle != middle){ //intersects with both middle and top
					g.drawLine((int)righttop.getX1(), (int)rectangle.getMinY() ,(int)righttop.getX2(), (int)middle.getMaxY());
					righttop.setLine(righttop.getX1(),righttop.getY1(),righttop.getX2(),middle.getMaxY());
				}
				else{
					g.drawLine((int)righttop.getX1(),(int) righttop.getY1(),(int)righttop.getX2(),(int)righttop.getY2());
					righttop.setLine(righttop.getX1(),righttop.getY1(),righttop.getX2(),righttop.getY2());
				}
			if(rightbottom.intersects(bottom) && rectangle != bottom){ //intersect w bottom
				if(rightbottom.intersects(middle) && rectangle != middle){
					g.drawLine((int)rightbottom.getX1(),(int) rightbottom.getY1(),(int)rightbottom.getX2(),(int)middle.getMinY());
					rightbottom.setLine(rightbottom.getX1(),rightbottom.getY1(),rightbottom.getX2(),middle.getMinY());
				}
				else{
					g.drawLine((int)rightbottom.getX1(),(int) rightbottom.getY1(),(int)rightbottom.getX2(),(int)bottom.getMinY());
					rightbottom.setLine(rightbottom.getX1(),rightbottom.getY1(),rightbottom.getX2(),bottom.getMinY());
				}
			}
			else
				if(rightbottom.intersects(middle) && rectangle != middle){
					g.drawLine((int)rightbottom.getX1(),(int) rightbottom.getY1(),(int)rightbottom.getX2(),(int)middle.getMinY());
					rightbottom.setLine(rightbottom.getX1(),rightbottom.getY1(),rightbottom.getX2(),middle.getMinY());
				}
				else{
					g.drawLine((int)rightbottom.getX1(),(int)rightbottom.getY1(),(int)rightbottom.getX2(),(int)rightbottom.getY2());
					rightbottom.setLine(rightbottom.getX1(),rightbottom.getY1(),rightbottom.getX2(),middle.getMinY());
				}
			
			midpoints.add(new Position((int)(lefttop.getX1()+lefttop.getX2())/2,(int)(lefttop.getY1() + lefttop.getY2())/2));
			midpoints.add(new Position((int)(righttop.getX1()+righttop.getX2())/2,(int)(righttop.getY1() + righttop.getY2())/2));
			midpoints.add(new Position((int)(leftbottom.getX1()+leftbottom.getX2())/2,(int)(leftbottom.getY1() + leftbottom.getY2())/2));
			midpoints.add(new Position((int)(rightbottom.getX1()+rightbottom.getX2())/2,(int)(rightbottom.getY1() + rightbottom.getY2())/2));

		}
		for(Line2D line : lines)
		{
			System.out.println("Line: x=" + line.getX1() + " y=" + line.getY1());
		}
		
		//draw rectangles on top of lines
		g.setColor(Color.GRAY);
		for(Rectangle rectangle : rectangles) 
		{
			g.fillRect(rectangle.x, rectangle.y, rectangle.width, rectangle.height);
		}
		System.out.println("MidPoints = " + midpoints);
		System.out.println("Size = " + midpoints.size());
		//this.getAdjacentMidPoints();
		this.drawConnections();
		System.out.println("HERE");
	}
	
	public void drawConnections()
	{
		Graphics2D g = (Graphics2D) canvasPanel.getGraphics();
		for(Line2D line : connections)
		{
			g.drawLine((int)line.getX1(),(int)line.getY1(),(int)line.getX2(),(int)line.getY2());
		}
	}
	
	public void drawPath(){
		//actually calculate the best path to end point here.
		Vertex source = null;
		Vertex dest = null;
		for(Vertex vertex : vertices)
		{
			if(vertex.point.equals(start))
				source = vertex;
			if(vertex.point.equals(end))
				dest = vertex;
		}
		Dijkstra.computePaths(source);
		List<Vertex> path = Dijkstra.getShortestPathTo(dest);
		System.out.println("Path = " + path);
	}
	
    public void drawMidPoints()                    
    {
    	Graphics2D g = (Graphics2D) canvasPanel.getGraphics();
    	g.setColor(Color.DARK_GRAY);
    	for(Position point : midpoints)
    	{
    		if(!point.equals(start) || !point.equals(end))
    			g.fillOval(point.x,point.y,5,5);
    	}
    }
	
    private class mouseEvent 
	implements MouseListener{

		@Override
		public void mouseClicked(MouseEvent evt) {
			int mouseX = evt.getX();
		    int mouseY = evt.getY();
		    int button = evt.getButton();
		    
		    Graphics2D g = (Graphics2D) canvasPanel.getGraphics();
		    int r = 10;
		    
		    //Add block if left click
		    if (button == 1)
		    {
		    	System.out.println("Left Click Detected!");
		    	switch (numLeftClicks) {
		            case 0:		 
			    		//draw 200x200 square
			    		rectangles[0] = new Rectangle(mouseX, mouseY, 200, 200);
			    		if (rectangles[0].contains(start) || rectangles[0].contains(end)){
			    			numLeftClicks--;
			    		}
			    		else{
				    		g.setColor(Color.GRAY);
				    		g.fillRect(mouseX, mouseY, 200, 200);
			    		}
			    		break;
		            case 1:
			    		//draw 150x150 square
			    		rectangles[1] = new Rectangle(mouseX, mouseY, 150, 150);
			    		if (rectangles[1].contains(start) || rectangles[1].contains(end)){
			    			numLeftClicks--;
			    		}
			    		else{
				    		g.setColor(Color.GRAY);
				    		g.fillRect(mouseX, mouseY, 150, 150);
			    		}
			    		break;
		            case 2:
			    		//draw 100x100 square
			    		rectangles[2] = new Rectangle(mouseX, mouseY, 100, 100);
			    		if (rectangles[2].contains(start) || rectangles[2].contains(end)){
			    			numLeftClicks--;
			    		}
			    		else{
				    		g.setColor(Color.GRAY);
				    		g.fillRect(mouseX, mouseY, 100, 100);
			    		}
			    		
			    		//after rectangles are drawn, need to draw cells on screen
			    		drawCells();
			    		break;
		    		default:
		    			break;
		    	}
		    	numLeftClicks++;
		    }
		    
		    //Add start/end point if right click
		    else if (button == 3) {
		    	System.out.println("Right Click Detected!");
		    	if (numLeftClicks>=3){
			    	switch (numRightClicks) {
			            case 0:
				    		//draw start point
			            	start.setLocation(mouseX,mouseY);
			            	if ( rectangles[2].contains(start) || rectangles[1].contains(start) || rectangles[0].contains(start)){
			            		numRightClicks--;
			            	}
			            	else{
					    		g.setColor(Color.GREEN);
					    		g.drawOval(mouseX,mouseY,r,r);
					    		g.fillOval(mouseX,mouseY,r,r);
					    		midpoints.add(new Position(start.x,start.y));
			            	}
				    		break;
			            case 1:
				    		//draw end point
			            	end.setLocation(mouseX,mouseY);
			            	if (rectangles[2].contains(end) || rectangles[1].contains(end) || rectangles[0].contains(end)){
			            		numRightClicks--;
			            	}
			            	
			            	else{
					    		g.setColor(Color.RED);
					    		g.drawOval(mouseX,mouseY,r,r);
					    		g.fillOval(mouseX,mouseY,r,r);
					    		midpoints.add(new Position(end.x,end.y));
			            	}
				    		break;
				    	default:
				    		break;
			    	}
			    	numRightClicks++;	
		    	}
		    }
		    
			if(numRightClicks >= 2 && numLeftClicks >= 3){
				getAdjacentMidPoints();
				drawConnections();
				drawPath();
				drawMidPoints();
			}
		}
		
		public void mousePressed(MouseEvent e) {}
		public void mouseReleased(MouseEvent e) {}
		public void mouseEntered(MouseEvent e) {}
		public void mouseExited(MouseEvent e) {}
	
	}
	


    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(MainWindow.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new MainWindow().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify                     
    private javax.swing.JPanel canvasPanel;
    private mouseEvent mouseListener;
    // End of variables declaration  
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        canvasPanel = new javax.swing.JPanel();
        canvasPanel.setBackground(Color.WHITE);
        mouseListener = new mouseEvent();
        canvasPanel.addMouseListener(mouseListener);
        
        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Aggie Robotics Project 5");
        setPreferredSize(new java.awt.Dimension(500, 500));
        setResizable(false);
        setSize(new java.awt.Dimension(500, 500));

        javax.swing.GroupLayout canvasPanelLayout = new javax.swing.GroupLayout(canvasPanel);
        canvasPanel.setLayout(canvasPanelLayout);
        canvasPanelLayout.setHorizontalGroup(
            canvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );
        canvasPanelLayout.setVerticalGroup(
            canvasPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 500, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(canvasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(canvasPanel, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold> 
}
