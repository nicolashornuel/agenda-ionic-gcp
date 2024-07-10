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
export abstract class FirestoreService {

  private firestore = inject(Firestore);
  protected path!: string;

  constructor(path: string) {
    this.path = path;
  }
  
  public async save(document: any): Promise<string> {
    const collectionRef = collection(this.firestore, this.path) as CollectionReference<any>;
    const docRef: DocumentReference<any> = doc(collectionRef);
    await setDoc(docRef, { ...document });
    return docRef.id;
  }
  
  public async createOne(document: any): Promise<string> {
    const functions: Functions = getFunctions();
    const onCallFunction = httpsCallable(functions, 'onCallCreateOne');
    return onCallFunction({ document, collection: this.path }) as unknown as string;
  }
}
