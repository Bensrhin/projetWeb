/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 * Author:  amalou
 * Created: 24 mars 2020
 */

create table utilisateur
(
    pseudonyme VARCHAR(20) primary key not null,
    password VARCHAR(60) not null,
) 