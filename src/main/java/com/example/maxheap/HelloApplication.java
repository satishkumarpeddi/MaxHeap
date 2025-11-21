package com.example.maxheap;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {
    MAXTree tree = new MAXTree();
    Group canvas = new Group();
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Label headingLabel = new Label("MAX Heap Visualization 🚀🚀 --> Developed By Satish Kumar Peddi ");

        headingLabel.setStyle("-fx-font-weight: bold;-fx-font-size:20");
        headingLabel.setAlignment(Pos.CENTER);
        TextField valueField = new TextField();
        valueField.setPromptText("Enter value ");
        Button insertBtn = new Button("Insert");
        Button deleteBtn = new Button("Delete");
        insertBtn.setStyle("-fx-font-weight: bold;-fx-font-size:12;-fx-background-color: #ff0000;-fx-text-fill: white;");
        deleteBtn.setStyle("-fx-font-weight: bold;-fx-font-size:12;-fx-background-color: #ff0000;-fx-text-fill: white;");
        insertBtn.setOnAction(e->{
            try{
                int value = Integer.parseInt(valueField.getText());
                tree.insert(value);
                drawTree();
                valueField.clear();
            }catch(Exception ignored){

            }
        });
        deleteBtn.setOnAction(e->{
            try {
                tree.delete();
                drawTree();
                valueField.clear();
            }catch(Exception ignored){

            }
        });
        Label valueLabel = new Label("Value:");
        valueLabel.setStyle("-fx-font-size: 15;-fx-font-weight: bold");
        HBox hBox = new HBox(10,valueLabel,valueField,insertBtn,deleteBtn);
        BorderPane root = new BorderPane();
        headingLabel.setPadding(new Insets(50));
        hBox.setPadding(new Insets(50));
        root.setTop(headingLabel);
        root.setRight(hBox);
        root.setCenter(canvas);
        root.setStyle("-fx-background-color: #e9e5cd;");
        Scene scene = new Scene(root, 900, 600);

        stage.setTitle("MAX Heap Visualization 🚀");
        stage.setScene(scene);
        stage.show();
    }
    void drawTree(){
        canvas.getChildren().clear();
        drawNode(tree.root,450,50,100);
    }
    void drawNode(Node root,double x,double y,double gap){
        if(root==null) return;
        root.x=x;
        root.y=y;
        if(root.left!=null){
            Line line = new Line(x,y,x-gap,y+70);
            canvas.getChildren().add(line);
            drawNode(root.left,x-gap,y+70,gap/2);
        }
        if(root.right!=null){
            Line line = new Line(x,y,x+gap,y+70);
            canvas.getChildren().add(line);
            drawNode(root.right,x+gap,y+70,gap/2);
        }
        Circle circle = new Circle(x,y,20, Color.AQUAMARINE);
        circle.setStroke(Color.BLACK);
        circle.setStrokeWidth(2);
        Text text = new Text(x-6,y+4,""+root.data);
        canvas.getChildren().addAll(circle,text);
    }
    static class Node{
        int data;
        double x,y;
        Node left;
        Node right;
        Node parent;
        Node(int data){
            this.data=data;
        }
    }

    static class MAXTree{
        Node root;
        void swap(Node a,Node b){
            int temp=a.data;
            a.data=b.data;
            b.data=temp;
        }
        int countNodes(Node root){
            if(root==null)
                return 0;
            return 1+countNodes(root.left)+countNodes(root.right);
        }
        void heapifyUp(Node root){
            while(root.parent!=null&&root.parent.data<root.data){
                swap(root,root.parent);
                root=root.parent;
            }
        }
        void heapifyDown(Node root){
            while(root!=null){
                Node largest=root;
                if(root.left!=null&&root.left.data>largest.data)
                    largest=root.left;
                if(root.right!=null&&root.right.data>largest.data)
                    largest=root.right;
                if(root!=largest){
                    swap(root,largest);
                    root=largest;
                }else{
                    break;
                }
            }
        }
        Node findPositionForInsertion(Node root,int count){
            int posLen=0;int pos[]=new int[32];
            while(count>1){
                pos[posLen++]=count%2;
                count/=2;
            }
            for(int i=posLen-1;i>=1;i--){
                if(pos[i]==0) root=root.left;
                else
                    root=root.right;
            }
            return root;
        }
        Node findPositionForDeletion(Node root,int count){
            int posLen=0,pos[] = new int[32];
            while(count>1){
                pos[posLen++]=count%2;
                count/=2;
            }
            for(int i=posLen-1;i>=0;i--){
                if(pos[i]==0) root=root.left;
                else
                    root=root.right;
            }
            return root;
        }
        Node insert(Node root,int data){

            if(root==null)
                return new Node(data);
            int NodesCount = countNodes(root);
            Node parent = findPositionForInsertion(root,NodesCount+1);
            Node newNode = new Node(data);
            newNode.parent=parent;
            if(parent.left==null)
                parent.left=newNode;
            else
                parent.right=newNode;
            heapifyUp(newNode);
            return root;
        }
        Node delete(Node root){
            if(root==null)
                return null;
            int NodesCount = countNodes(root);
            if(NodesCount==1){
                return null;
            }
            Node lastNode = findPositionForDeletion(root,NodesCount);
            root.data=lastNode.data;
            if(lastNode.parent.left==lastNode)
                lastNode.parent.left=null;
            else
                lastNode.parent.right=null;
            heapifyDown(root);
            return root;
        }
        void insert(int data){ root=insert(root,data);}
        void delete(){root= delete(root);}


    }


}
