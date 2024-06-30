import { Injectable, inject } from '@angular/core';
import {
  CollectionReference,
  DocumentReference,
  Firestore,
  collection,
  doc,
  setDoc
} from '@angular/fire/firestore';
import { Functions, getFunctions, httpsCallable } from "firebase/functions";

@Injectable({
  providedIn: 'root'
})
export class FirestoreService {

  private readonly collection = "messages";

  constructor(private firestore: Firestore) {}
  
  public async save(document: any): Promise<string> {
    console.log(document);
    
    const collectionRef = collection(this.firestore, this.collection) as CollectionReference<any>;
    const docRef: DocumentReference<any> = doc(collectionRef);
    await setDoc(docRef, { ...document });
    return docRef.id;
  }
  
  public async createOne(document: any): Promise<string> {
    const functions: Functions = getFunctions();
    const onCallFunction = httpsCallable(functions, 'onCallCreateOne');
    return onCallFunction({ document, collection: this.collection }) as unknown as string;
  }
}
